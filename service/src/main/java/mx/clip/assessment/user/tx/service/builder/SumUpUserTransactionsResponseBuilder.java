package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;

public class SumUpUserTransactionsResponseBuilder {

    private SumUpUserTransactionsResponse response;

    public SumUpUserTransactionsResponseBuilder() {
        response = new SumUpUserTransactionsResponse();
    }

    public SumUpUserTransactionsResponse build() {
        return response;
    }

    public SumUpUserTransactionsResponseBuilder withUserId(String userId) {
        response.setUserId(userId);
        return this;
    }

    public SumUpUserTransactionsResponseBuilder withSum(String sum) {
        response.setSum(sum);
        return this;
    }
}
