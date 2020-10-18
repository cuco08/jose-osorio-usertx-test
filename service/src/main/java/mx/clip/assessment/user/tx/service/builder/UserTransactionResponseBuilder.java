package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.UserTransaction;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;
import mx.clip.assessment.user.tx.service.mapper.UserTransactionMapper;

public class UserTransactionResponseBuilder {

    private UserTransactionResponse response;

    private UserTransactionMapper mapper;

    public UserTransactionResponseBuilder(String dateFormatPattern) {
        response = new UserTransactionResponse();
        mapper = new UserTransactionMapper(dateFormatPattern);
    }

    public UserTransactionResponseBuilder from(mx.clip.assessment.user.tx.dao.entities.UserTransaction userTransactionEntity) {

        return this.withUserTransaction(mapper.map(userTransactionEntity));
    }

    public UserTransactionResponse build() {
        return response;
    }

    public UserTransactionResponseBuilder withUserTransaction(UserTransaction userTransaction) {
        response.setUserTransaction(userTransaction);
        return this;
    }
}
