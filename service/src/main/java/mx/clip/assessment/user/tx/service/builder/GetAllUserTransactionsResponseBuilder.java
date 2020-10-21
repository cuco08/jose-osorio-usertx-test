package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserTransaction;
import mx.clip.assessment.user.tx.service.mapper.UserTransactionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserTransactionsResponseBuilder {

    private GetAllUserTransactionsResponse response;

    private UserTransactionMapper mapper;

    public GetAllUserTransactionsResponseBuilder(String dateFormatPattern) {
        response = new GetAllUserTransactionsResponse();
        mapper = new UserTransactionMapper(dateFormatPattern);
    }

    public GetAllUserTransactionsResponse build() {
        return response;
    }

    public GetAllUserTransactionsResponseBuilder from(List<mx.clip.assessment.user.tx.dao.entities.UserTransaction> userTransactionEntityList) {

        return this.withUserTransactions(
            userTransactionEntityList.stream().map(txn -> mapper.map(txn)).collect(Collectors.toList())
        );
    }

    public GetAllUserTransactionsResponseBuilder withUserTransactions(List<UserTransaction> userTransactionList) {
        if (response.getUserTransactions() == null) {
            response.setUserTransactions(new ArrayList<>());
        }
        response.getUserTransactions().addAll(userTransactionList);
        return this;
    }
}
