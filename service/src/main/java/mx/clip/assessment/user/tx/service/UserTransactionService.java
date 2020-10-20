package mx.clip.assessment.user.tx.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import mx.clip.assessment.user.tx.api.UserTransactionsApi;
import mx.clip.assessment.user.tx.api.model.WeeklyReport;
import mx.clip.assessment.user.tx.service.builder.GetAllUserTransactionsResponseBuilder;
import mx.clip.assessment.user.tx.service.builder.SumUpUserTransactionsResponseBuilder;
import mx.clip.assessment.user.tx.service.builder.UserTransactionResponseBuilder;
import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionsReportResponse;
import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;
import mx.clip.assessment.user.tx.dao.UserTransactionRepository;
import mx.clip.assessment.user.tx.dao.entities.UserTransaction;
import mx.clip.assessment.user.tx.service.builder.UserTransactionEntityBuilder;

import mx.clip.assessment.user.tx.service.builder.WeeklyReportBuilder;
import mx.clip.assessment.user.tx.service.exception.ServiceResultCode;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;
import mx.clip.assessment.user.tx.service.factory.LocalDateTimeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class UserTransactionService implements UserTransactionsApi {

    private final UserTransactionRepository repository;

    private final LocalDateTimeFactory localDateTimeFactory;

    @Value("${user.transactions.datetime.pattern}")
    private String dateFormatPattern;

    @Value("${user.transactions.datetime.pattern.report}")
    private String reportDateFormatPattern;

    @Override
    public UserTransactionResponse addUserTransaction(AddUserTransactionRequest request) {

        final UserTransaction newUserTransactionEntity = repository.save(
                new UserTransactionEntityBuilder(dateFormatPattern).from(request).build()
        );

        return new UserTransactionResponseBuilder(dateFormatPattern)
                .from(newUserTransactionEntity)
                .build();
    }

    @Override
    public UserTransactionResponse getUserTransaction(GetUserTransactionRequest request) {

        final UserTransaction userTransactionEntity = repository.findByUserId(request.getUserId())
                .stream()
                .filter(txn -> txn.getTransactionId().equals(request.getTransactionId()))
                .findFirst()
                .orElseThrow(() -> new UserTransactionServiceException("Transaction not found for the given user.",
                        ServiceResultCode.NO_DATA_FOUND));

        return new UserTransactionResponseBuilder(dateFormatPattern)
                .from(userTransactionEntity)
                .build();
    }

    @Override
    public GetAllUserTransactionsResponse getAllUserTransactions(UserIdentifierRequest request) {

        final List<UserTransaction> userTransactionEntityList = findSortedUserTransactions(request.getUserId());

        log.debug("Total number of transactions for user={} is {}", request.getUserId(), userTransactionEntityList.size());

        return new GetAllUserTransactionsResponseBuilder(dateFormatPattern)
                .from(userTransactionEntityList)
                .build();
    }

    @Override
    public SumUpUserTransactionsResponse sumUpUserTransactions(UserIdentifierRequest request) {

        final double totalAmount = repository.findByUserId(request.getUserId())
                .stream()
                .mapToDouble(UserTransaction::getAmount)
                .reduce(0.0, (subtotal, txnAmount) -> subtotal + txnAmount);

        log.debug("Total amount for transactions of user={} is {}", totalAmount, request.getUserId());

        return new SumUpUserTransactionsResponseBuilder()
                .withUserId(request.getUserId())
                .withSum(String.valueOf(totalAmount))
                .build();
    }

    @Override
    public GetUserTransactionsReportResponse getUserTransactionsReport(UserIdentifierRequest request) {
        final GetUserTransactionsReportResponse response = new GetUserTransactionsReportResponse();
        response.setWeeklyReports(new ArrayList<>());

        final Queue<UserTransaction> queue = new LinkedList<>(findSortedUserTransactions(request.getUserId()));
        double cumulativeAmount = 0;

        log.debug("Creating report for {} transactions", queue.size());

        while (!queue.isEmpty()) {

            LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(queue.peek().getDate());
            LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(queue.peek().getDate());

            log.debug("Setting startDate={} and endDate={} for given initialDate={}", startDate, endDate, queue.peek().getDate());

            final List<UserTransaction> weeklyUserTxns = queue.stream()
                    .filter(txn -> isInRange(txn.getDate(), startDate, endDate))
                    .collect(Collectors.toList());

            log.debug("{} transactions found for the given start-end dates.", weeklyUserTxns.size());

            double amount =  weeklyUserTxns.stream()
                    .mapToDouble(UserTransaction::getAmount)
                    .reduce(0.0, (subtotal, txnAmount) -> {
                        queue.remove();
                        return subtotal + txnAmount;
                    });

            final WeeklyReport weeklyReport = new WeeklyReportBuilder()
                    .withUserId(request.getUserId())
                    .withStartWeek(startDate.format(DateTimeFormatter.ofPattern(reportDateFormatPattern)))
                    .withFinishWeek(endDate.format(DateTimeFormatter.ofPattern(reportDateFormatPattern)))
                    .withQuantity(weeklyUserTxns.size())
                    .withAmount(amount)
                    .withTotalAmount(cumulativeAmount)
                    .build();

            cumulativeAmount += amount;
            response.addWeeklyReportsItem(weeklyReport);
        }

        log.info("Cumulative amount={} for all the transactions of user={}",cumulativeAmount, request.getUserId());

        return response;
    }

    @Override
    public UserTransactionResponse getRandomUserTransaction() {
        throw new UserTransactionServiceException("Not implemented yet.", ServiceResultCode.NOT_IMPLEMENTED);
    }

    private List<UserTransaction> findSortedUserTransactions(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(UserTransaction::getDate))
                .collect(Collectors.toList());
    }

    private static boolean isInRange(LocalDateTime dateTime, LocalDateTime startDate, LocalDateTime endDate) {
        return dateTime.isAfter(startDate.minusDays(1)) && dateTime.isBefore(endDate.plusDays(1));
    }
}
