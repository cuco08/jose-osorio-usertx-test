package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.UserTransaction;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;
import mx.clip.assessment.user.tx.service.mapper.UserTransactionMapper;
import org.springframework.beans.BeanUtils;

public class UserTransactionResponseBuilder {

    private UserTransactionResponse response;

    private UserTransactionMapper mapper;

    public UserTransactionResponseBuilder(String dateFormatPattern) {
        response = new UserTransactionResponse();
        mapper = new UserTransactionMapper(dateFormatPattern);
    }

    public UserTransactionResponseBuilder from(mx.clip.assessment.user.tx.dao.entities.UserTransaction userTransactionEntity) {

        final UserTransaction userTransaction = mapper.map(userTransactionEntity);
        BeanUtils.copyProperties(userTransaction, response);
        return this;
    }

    public UserTransactionResponse build() {
        return response;
    }
}
