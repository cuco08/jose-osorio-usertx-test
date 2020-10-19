package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;

public class UserIdentifierRequestBuilder {

    private UserIdentifierRequest request;

    public UserIdentifierRequestBuilder() {
        request = new UserIdentifierRequest();
    }

    public UserIdentifierRequest build() {
        return request;
    }

    public UserIdentifierRequestBuilder withUserId(String userId) {
        request.setUserId(userId);
        return this;
    }
}
