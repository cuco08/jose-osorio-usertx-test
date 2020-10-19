package mx.clip.assessment.user.tx.service.factory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeFactory {

    public LocalDateTime getStartLocalDateTime(LocalDateTime baseDate) {
        LocalDateTime tentativeStartDate = baseDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));

        if (tentativeStartDate.getDayOfMonth() > baseDate.getDayOfMonth()) {
            tentativeStartDate = baseDate.with(TemporalAdjusters.firstDayOfMonth());
        }

        return tentativeStartDate;
    }

    public LocalDateTime getEndLocalDateTime(LocalDateTime baseDate) {
        LocalDateTime tentativeEndDate = baseDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));

        if (tentativeEndDate.getDayOfMonth() < baseDate.getDayOfMonth()) {
            tentativeEndDate = baseDate.with(TemporalAdjusters.lastDayOfMonth());
        }

        return tentativeEndDate;
    }
}
