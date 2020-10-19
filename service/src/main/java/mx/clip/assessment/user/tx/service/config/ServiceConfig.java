package mx.clip.assessment.user.tx.service.config;

import mx.clip.assessment.user.tx.service.factory.LocalDateTimeFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public LocalDateTimeFactory getLocalDateTimeFactory() {
        return new LocalDateTimeFactory();
    }
}
