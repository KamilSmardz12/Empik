package pl.ks.empiktask.util.time;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {

    private final Clock clock;

    public LocalDateTimeProvider(Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
