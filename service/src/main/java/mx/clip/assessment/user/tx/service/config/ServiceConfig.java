package mx.clip.assessment.user.tx.service.config;

import mx.clip.assessment.user.tx.service.factory.LocalDateTimeFactory;

import mx.clip.assessment.user.tx.service.random.LinearCongruentialGenerator;
import mx.clip.assessment.user.tx.service.random.PseudoRandomGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class ServiceConfig {

    @Bean
    public LocalDateTimeFactory getLocalDateTimeFactory() {
        return new LocalDateTimeFactory();
    }

    @Bean
    public PseudoRandomGenerator getPseudoRandomGenerator() {
        int seed = (int)(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() % Integer.MAX_VALUE);

        return new LinearCongruentialGenerator(seed);
    }
}
