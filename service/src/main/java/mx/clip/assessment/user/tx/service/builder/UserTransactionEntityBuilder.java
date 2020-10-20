package mx.clip.assessment.user.tx.service.builder;

import lombok.extern.slf4j.Slf4j;
import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.dao.entities.UserTransaction;
import mx.clip.assessment.user.tx.service.exception.ServiceResultCode;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class UserTransactionEntityBuilder {

    private UserTransaction  userTransaction;

    private SimpleDateFormat simpleDateFormat;

    public UserTransactionEntityBuilder(String dateFormatPattern) {
        userTransaction = new UserTransaction();
        simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    public UserTransactionEntityBuilder from(AddUserTransactionRequest request) {

        this.withUserId(request.getUserId())
            .withTransactionId(UUID.randomUUID().toString())
            .withAmount(request.getAmount())
            .withDescription(request.getDescription());

        try {
            final Date txnDate = simpleDateFormat.parse(request.getDate());
            this.withDate(LocalDateTime.ofInstant(txnDate.toInstant(), ZoneId.systemDefault()));
        } catch (Exception e) {
            log.error("An error occurred while trying to parse the transaction date from the request.", e);
            throw new UserTransactionServiceException(
                    "Transaction date is not in the correct format: yyyy-mm-dd.", e, ServiceResultCode.BAD_REQUEST);
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
