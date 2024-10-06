package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;

// BEGIN
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean
    public Daytime getDayTime() {
        LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(22, 0))) {
            return new Day();
        }
        return new Night();
    }
    // END
}
