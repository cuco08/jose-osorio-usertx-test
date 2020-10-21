package mx.clip.assessment.user.tx.service.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTransactionServiceExceptionTest {

    @Test
    void shouldGetDomainResultCode() {
        UserTransactionServiceException ex =
                new UserTransactionServiceException("message", ServiceResultCode.NO_DATA_FOUND);

        assertThat(ex.getDomainResultCode()).isNotNull();
        assertThat(ex.getDomainResultCode().getStatus()).isEqualTo(404);
    }
}
