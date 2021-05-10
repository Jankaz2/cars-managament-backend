package kazmierczak.jan.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

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

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
