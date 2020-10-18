package mx.clip.assessment.user.tx.service;

import lombok.RequiredArgsConstructor;

import mx.clip.assessment.user.tx.api.UserTransactionsApi;
import mx.clip.assessment.user.tx.service.builder.GetAllUserTransactionsResponseBuilder;
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

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTransactionService implements UserTransactionsApi {

    private final UserTransactionRepository repository;

    private final String dateFormatPattern = "yyyy-MM--dd";

    @Override
    public UserTransactionResponse addUserTransaction(AddUserTransactionRequest request) {

        final UserTransaction newUserTransactionEntity = repository.save(
                new UserTransactionEntityBuilder(dateFormatPattern).from(request.getUserTransaction()).build()
        );

        return new UserTransactionResponseBuilder(dateFormatPattern).from(newUserTransactionEntity).build();
    }

    @Override
    public UserTransactionResponse getUserTransaction(GetUserTransactionRequest request) {
        return null;
    }

    @Override
    public GetAllUserTransactionsResponse getAllUserTransactions(UserIdentifierRequest request) {

        final List<UserTransaction> userTransactionEntityList = repository.findByUserId(request.getUserId());

        return new GetAllUserTransactionsResponseBuilder(dateFormatPattern).from(userTransactionEntityList).build();
    }

    @Override
    public SumUpUserTransactionsResponse sumUpUserTransactions(UserIdentifierRequest request) {
        return null;
    }

    @Override
    public GetUserTransactionsReportResponse getUserTransactionsReport(UserIdentifierRequest request) {
        return null;
    }

    @Override
    public UserTransactionResponse getRandomUserTransaction() {
        return null;
    }
}
