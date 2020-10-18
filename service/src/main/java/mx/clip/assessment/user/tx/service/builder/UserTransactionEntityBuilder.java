package mx.clip.assessment.user.tx.service.builder;


import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class UserTransactionEntityBuilder {

    private UserTransaction  userTransaction;

    private SimpleDateFormat simpleDateFormat;

    public UserTransactionEntityBuilder(String dateFormatPattern) {
        userTransaction = new UserTransaction();
        simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    public UserTransactionEntityBuilder from(mx.clip.assessment.user.tx.api.model.UserTransaction fromUserTransaction) {

        this.withUserId(fromUserTransaction.getUserId())
            .withTransactionId(UUID.randomUUID().toString())
            .withAmount(fromUserTransaction.getAmount())
            .withDescription(fromUserTransaction.getDescription());

        try {
            final Date txnDate = simpleDateFormat.parse(fromUserTransaction.getDate());
            this.withDate(LocalDateTime.ofInstant(txnDate.toInstant(), ZoneId.systemDefault()));
        } catch (Exception e) {

        }

        return this;
    }

    public UserTransaction build() {
        return userTransaction;
    }

    public UserTransactionEntityBuilder withUserId(String userId) {
        userTransaction.setUserId(userId);
        return this;
    }

    public UserTransactionEntityBuilder withTransactionId(String transactionId) {
        userTransaction.setTransactionId(transactionId);
        return this;
    }

    public UserTransactionEntityBuilder withAmount(double amount) {
        userTransaction.setAmount(amount);
        return this;
    }

    public UserTransactionEntityBuilder withDescription(String description) {
        userTransaction.setDescription(description);
        return this;
    }

    public UserTransactionEntityBuilder withDate(LocalDateTime date) {
        userTransaction.setDate(date);
        return this;
    }
}
