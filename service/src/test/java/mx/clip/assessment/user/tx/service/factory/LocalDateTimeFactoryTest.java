package mx.clip.assessment.user.tx.service.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LocalDateTimeFactoryTest {

    private LocalDateTimeFactory localDateTimeFactory;

    @BeforeEach
    public void setup() {
        localDateTimeFactory = new LocalDateTimeFactory();
    }

    @Test
    void startDate() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 4, 12, 00);

        LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(dateTime);

        System.out.println("Start date: " + startDate);

        LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(dateTime);

        System.out.println("End date: " + endDate);
    }

    @Test
    void startDate2() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 11, 30, 12, 00);

        LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(dateTime);

        System.out.println("Start date: " + startDate);

        LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(dateTime);

        System.out.println("End date: " + endDate);
    }

    @Test
    void startDate3() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 11, 25, 12, 00);

        LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(dateTime);

        System.out.println("Start date: " + startDate);

        LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(dateTime);

        System.out.println("End date: " + endDate);
    }
}
