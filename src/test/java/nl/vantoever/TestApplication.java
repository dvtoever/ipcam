package nl.vantoever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;

/**
 * Spring boot application for test purposes.
 *
 * The most important is that we set the active profile to "test", so the application-test.properties file
 * will be used instead of the normal application.properties. This will create an in memory database for the junit
 * tests instead of using the real database.
 */
@SpringBootApplication
@ActiveProfiles("test")
public class TestApplication{

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);

    }
}
