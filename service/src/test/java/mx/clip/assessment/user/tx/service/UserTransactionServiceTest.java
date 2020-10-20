package mx.clip.assessment.user.tx.service;

import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionsReportResponse;
import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;
import mx.clip.assessment.user.tx.dao.UserTransactionRepository;
import mx.clip.assessment.user.tx.dao.entities.UserTransaction;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;
import mx.clip.assessment.user.tx.service.factory.LocalDateTimeFactory;

import static mx.clip.assessment.user.tx.service.util.ServiceTestData.getUserTransactionEntityList;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static mx.clip.assessment.user.tx.service.util.ServiceTestData.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTransactionServiceTest {

    private UserTransactionService serviceApi;

    private final UserTransactionRepository repository = mock(UserTransactionRepository.class);

    @BeforeEach
    void setup() {
        serviceApi = new UserTransactionService(repository, new LocalDateTimeFactory());
        serviceApi.setDateFormatPattern("yyyy-mm-dd");
        serviceApi.setReportDateFormatPattern("yyyy-mm-dd EEEE");
    }

    @Test
    void shouldAddUserTransaction() {
        when(repository.save(any())).thenReturn(getUserTransactionEntity());

        UserTransactionResponse response = serviceApi.addUserTransaction(getAddUserTransactionRequest());

        assertThat(response).isNotNull();
        assertThat(response.getTransactionId()).isNotNull();
        assertThat(response.getDate()).isNotNull();
    }

    @Test()
    void shouldNotAddUserTransaction_BadDate() {
        when(repository.save(any())).thenReturn(getUserTransactionEntity());

        assertThrows(UserTransactionServiceException.class, () ->
                serviceApi.addUserTransaction(getAddUserTransactionBadDateRequest())
        );
    }

    @Test
    void shouldGetUserTransaction() {

        UserTransaction userTransaction = getUserTransactionEntity();
        String userId = userTransaction.getUserId();
        String transactionId = userTransaction.getTransactionId();

        List<UserTransaction> customList = getUserTransactionEntityList();
        customList.add(userTransaction);

        when(repository.findByUserId(any())).thenReturn(customList);
        UserTransactionResponse response =
                serviceApi.getUserTransaction(getGetUserTransactionRequest(userId, transactionId));

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getTransactionId()).isEqualTo(transactionId);
    }

    @Test
    void shouldNotGetUserTransaction_EmptyList() {

        when(repository.findByUserId(any())).thenReturn(Collections.emptyList());

        assertThrows(UserTransactionServiceException.class, () ->
                serviceApi.getUserTransaction(getGetRandomUserTransactionRequest())
        );
    }

    @Test
    void shouldNotGetUserTransaction_NoTransactionFound() {

        when(repository.findByUserId(any())).thenReturn(getUserTransactionEntityList());

        assertThrows(UserTransactionServiceException.class, () ->
                serviceApi.getUserTransaction(getGetRandomUserTransactionRequest())
        );
    }

    @Test
    void shouldGetAllUserTransactions() {

        List<UserTransaction> customList = getUserTransactionEntityList();

        when(repository.findByUserId(any())).thenReturn(customList);

        GetAllUserTransactionsResponse response = serviceApi.getAllUserTransactions(getRandomUserIdentifierRequest());

        assertThat(response).isNotNull();
        assertThat(response.getUserTransactions()).isNotEmpty();
        assertThat(response.getUserTransactions().size()).isEqualTo(customList.size());
    }

    @Test
    void shouldNotGetAllUserTransactions_NoTransactionsFound() {

        when(repository.findByUserId(any())).thenReturn(Collections.emptyList());

        GetAllUserTransactionsResponse response = serviceApi.getAllUserTransactions(getRandomUserIdentifierRequest());

        assertThat(response).isNotNull();
        assertThat(response.getUserTransactions()).isEmpty();
    }

    @Test
    void shouldSumUpUserTransactions() {
        List<UserTransaction> customList = getUserTransactionEntityList();
        for (UserTransaction txn : customList) {
            txn.setAmount(10.00);
        }
        double totalAmount = 10 * customList.size();

        when(repository.findByUserId(any())).thenReturn(customList);
        SumUpUserTransactionsResponse response = serviceApi.sumUpUserTransactions(getRandomUserIdentifierRequest());

        assertThat(response).isNotNull();
        assertThat(response.getSum()).isEqualTo(String.valueOf(totalAmount));
    }

    @Test
    void shouldSumUpUserTransactions_NoTransactionsFound() {

        when(repository.findByUserId(any())).thenReturn(Collections.emptyList());
        SumUpUserTransactionsResponse response = serviceApi.sumUpUserTransactions(getRandomUserIdentifierRequest());

        assertThat(response).isNotNull();
        assertThat(response.getSum()).isEqualTo("0.0");
    }

    @Test
    void shouldGetUserTransactionsReport_FridayToThursday() {

        UserTransaction userTransaction = getUserTransactionEntity(UUID.randomUUID().toString(),
                LocalDateTime.of(2020, 5, 14, 12, 00));

        shouldGetUserTransactionsReport(userTransaction, DayOfWeek.FRIDAY, DayOfWeek.THURSDAY);
    }

    @Test
    void shouldGetUserTransactionsReport_TuesdayToSaturday() {

        UserTransaction userTransaction = getUserTransactionEntity(UUID.randomUUID().toString(),
                LocalDateTime.of(2020, 9, 3, 12, 00));

        shouldGetUserTransactionsReport(userTransaction, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);
    }

    @Test
    void shouldGetUserTransactionsReport_MondayToThursday() {

        UserTransaction userTransaction = getUserTransactionEntity(UUID.randomUUID().toString(),
                LocalDateTime.of(2020, 2, 29, 12, 00));

        shouldGetUserTransactionsReport(userTransaction, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    }

    private void shouldGetUserTransactionsReport(UserTransaction userTransaction, DayOfWeek start, DayOfWeek end) {

        List<UserTransaction> userTransactionList = new ArrayList<>();
        userTransactionList.add(userTransaction);

        when(repository.findByUserId(any())).thenReturn(userTransactionList);
        GetUserTransactionsReportResponse response = serviceApi.getUserTransactionsReport(getRandomUserIdentifierRequest());

        assertThat(response).isNotNull();
        assertThat(response.getWeeklyReports()).isNotEmpty();
        assertThat(response.getWeeklyReports().size()).isEqualTo(1);
        assertThat(response.getWeeklyReports().get(0).getStartWeek()).containsIgnoringCase(start.name());
        assertThat(response.getWeeklyReports().get(0).getFinishWeek()).containsIgnoringCase(end.name());
    }

    @Test
    void shouldGetUserTransactionsReport_NoTransactionsFound() {

        when(repository.findByUserId(any())).thenReturn(Collections.emptyList());
        GetUserTransactionsReportResponse response = serviceApi.getUserTransactionsReport(getRandomUserIdentifierRequest());

        assertThat(response).isNotNull();
        assertThat(response.getWeeklyReports()).isEmpty();
    }

    @Test
    void shouldGetRandomUserTransaction() {
        assertThrows(UserTransactionServiceException.class, () ->
            serviceApi.getRandomUserTransaction()
        );
    }
}
