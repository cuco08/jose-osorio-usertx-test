package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.GetUserTransactionRequest;

public class GetUserTransactionRequestBuilder {

    private GetUserTransactionRequest request;

    public GetUserTransactionRequestBuilder() {
        request = new GetUserTransactionRequest();
    }

    public GetUserTransactionRequest build() {
        return request;
    }

    public GetUserTransactionRequestBuilder withUserId(String userId) {
        request.setUserId(userId);
        return this;
    }

    public GetUserTransactionRequestBuilder withTransactionId(String transactionId) {
        request.setTransactionId(transactionId);
        return this;
    }
}
