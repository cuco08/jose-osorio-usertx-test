package mx.clip.assessment.user.tx.data;

import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserTransactionData {

    private UserTransactionData() {}

    public static UserTransaction createUserTransaction(String userId, LocalDateTime date) {
        return new UserTransaction().builder()
                .userId(userId)
                .transactionId(UUID.randomUUID().toString())
                .description("My new transaction - " + UUID.randomUUID().toString())
                .amount(Math.round(Math.random() * 100.00))
                .date(date)
                .build();
    }
}
