package mx.clip.assessment.user.tx.service.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class LocalDateTimeFactoryTest {

    private LocalDateTimeFactory localDateTimeFactory;

    @BeforeEach
    public void setup() {
        localDateTimeFactory = new LocalDateTimeFactory();
    }

    @Test
    void shouldGenerateStartAndEndDates_FridayThursday() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 9, 12, 00);

        LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(dateTime);
        log.debug("Start date: {}", startDate);
        assertThat(startDate).isBeforeOrEqualTo(dateTime);
        assertThat(startDate.getDayOfWeek()).isEqualByComparingTo(DayOfWeek.FRIDAY);

        LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(dateTime);
        log.debug("End date: {}", endDate);
        assertThat(endDate).isAfterOrEqualTo(dateTime);
        assertThat(endDate.getDayOfWeek()).isEqualByComparingTo(DayOfWeek.THURSDAY);
    }

    @Test
    void shouldGenerateStartAndEndDates_FridayNotThursday() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 11, 30, 12, 00);

        LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(dateTime);
        log.debug("Start date: {}", startDate);
        assertThat(startDate).isBeforeOrEqualTo(dateTime);
        assertThat(startDate.getDayOfWeek()).isEqualByComparingTo(DayOfWeek.FRIDAY);

        LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(dateTime);
        log.debug("End date: {}", endDate);
        assertThat(endDate).isAfterOrEqualTo(dateTime);
        assertThat(endDate.getDayOfWeek()).isNotEqualByComparingTo(DayOfWeek.THURSDAY);
    }

    @Test
    void shouldGenerateStartAndEndDates_NotFridayThursday() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 00);

        LocalDateTime startDate = localDateTimeFactory.getStartLocalDateTime(dateTime);
        log.debug("Start date: {}", startDate);
        assertThat(startDate).isBeforeOrEqualTo(dateTime);
        assertThat(startDate.getDayOfWeek()).isNotEqualByComparingTo(DayOfWeek.FRIDAY);

        LocalDateTime endDate = localDateTimeFactory.getEndLocalDateTime(dateTime);
        log.debug("End date: {}", endDate);
        assertThat(endDate).isAfterOrEqualTo(dateTime);
        assertThat(endDate.getDayOfWeek()).isEqualByComparingTo(DayOfWeek.THURSDAY);
    }
}
