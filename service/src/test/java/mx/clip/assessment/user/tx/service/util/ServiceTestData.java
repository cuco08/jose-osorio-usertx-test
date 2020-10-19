package mx.clip.assessment.user.tx.service.util;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;
import mx.clip.assessment.user.tx.dao.entities.UserTransaction;
import mx.clip.assessment.user.tx.service.builder.GetUserTransactionRequestBuilder;
import mx.clip.assessment.user.tx.service.builder.UserIdentifierRequestBuilder;
import mx.clip.assessment.user.tx.service.builder.UserTransactionEntityBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceTestData {

    private ServiceTestData() {}

    public static UserTransaction getUserTransactionEntity() {
        UserTransaction userTransaction = new UserTransactionEntityBuilder("yyyy-mm-dd")
                .withUserId(UUID.randomUUID().toString())
                .withTransactionId(UUID.randomUUID().toString())
                .withDescription("Test description")
                .withAmount(10.0)
                .withDate(LocalDateTime.now())
                .build();

        return userTransaction;
    }

    public static List<UserTransaction> getUserTransactionEntityList() {
        List<UserTransaction> list = new ArrayList<>();
        list.add(getUserTransactionEntity());
        list.add(getUserTransactionEntity());
        list.add(getUserTransactionEntity());

        return list;
    }

    public static AddUserTransactionRequest getAddUserTransactionRequest() {
        AddUserTransactionRequest request = new AddUserTransactionRequest();

        request.setUserId(UUID.randomUUID().toString());
        request.setDescription("Test description");
        request.setAmount(11.0);
        request.setDate("2020-10-19");

        return request;
    }

    public static AddUserTransactionRequest getAddUserTransactionBadDateRequest() {
        AddUserTransactionRequest request = getAddUserTransactionRequest();
        request.setDate("20201019");

        return request;
    }

    public static GetUserTransactionRequest getGetRandomUserTransactionRequest() {
        return new GetUserTransactionRequestBuilder()
                .withUserId(UUID.randomUUID().toString())
                .withTransactionId(UUID.randomUUID().toString())
                .build();
    }

    public static GetUserTransactionRequest getGetUserTransactionRequest(String userId, String transactionId) {
        return new GetUserTransactionRequestBuilder()
                .withUserId(userId)
                .withTransactionId(transactionId)
                .build();
    }

    public static UserIdentifierRequest getRandomUserIdentifierRequest() {
        return new UserIdentifierRequestBuilder()
                .withUserId(UUID.randomUUID().toString())
                .build();
    }
}
