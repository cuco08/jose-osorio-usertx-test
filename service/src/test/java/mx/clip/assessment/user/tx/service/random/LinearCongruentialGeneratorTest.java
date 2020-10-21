package mx.clip.assessment.user.tx.service.random;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LinearCongruentialGeneratorTest {

    @Test
    void nextInt() {
        int seed = (int)(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() % Integer.MAX_VALUE);

        PseudoRandomGenerator generator = new LinearCongruentialGenerator(seed);

        int value1 = generator.nextInt();
        log.debug("Next random value={}", value1);

        int value2 = generator.nextInt();
        log.debug("Next random value={}", value2);

        assertThat(value1).isLessThan(Integer.MAX_VALUE);
        assertThat(value2).isLessThan(Integer.MAX_VALUE);
        assertThat(value1).isNotEqualTo(value2);
    }
}
