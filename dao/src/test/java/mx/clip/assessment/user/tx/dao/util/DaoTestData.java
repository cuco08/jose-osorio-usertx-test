package mx.clip.assessment.user.tx.dao.util;

import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class DaoTestData {

    private DaoTestData() {}

    public static UserTransaction getUserTransaction(double amount, String description) {
        return UserTransaction.builder()
                .userId(UUID.randomUUID().toString())
                .transactionId(UUID.randomUUID().toString())
                .amount(amount)
                .description(description)
                .date(LocalDateTime.now())
                .build();
    }
}
