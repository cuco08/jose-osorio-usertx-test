package mx.clip.assessment.user.tx.service.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceConfigTest {

    @Test
    void shouldGetLocalDateTimeFactory() {
        assertThat(new ServiceConfig().getLocalDateTimeFactory()).isNotNull();
    }
}
