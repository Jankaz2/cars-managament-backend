package kazmierczak.jan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("kazmierczak.jan")
public class AppSpringConfig {
    @Configuration
    @Profile("dev")
    @PropertySource("classpath:application.properties")
    static class DevConfig{}

    @Configuration
    @Profile("test")
    @PropertySource("classpath:application-test.properties")
    static class TestConfig{}
}
