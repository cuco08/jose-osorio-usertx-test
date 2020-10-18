package mx.clip.assessment.user.tx.dao.util;

import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import java.util.Calendar;
import java.util.UUID;

public class TestData {

    private TestData() {}

    public static UserTransaction getUserTransaction(double amount, String description) {
        return UserTransaction.builder()
                .userId(UUID.randomUUID().toString())
                .transactionId(UUID.randomUUID().toString())
                .amount(amount)
                .description(description)
                .date(Calendar.getInstance().getTime())
                .build();
    }
}
