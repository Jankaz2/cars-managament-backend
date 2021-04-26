package kazmierczak.jan.tokens;

import kazmierczak.jan.domain.User;
import kazmierczak.jan.domain.types.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InMemoryUsersService {
    private PasswordEncoder passwordEncoder;

    private List<User> users = List.of(
            User
                    .builder()
                    .id(1L)
                    .username("u")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.USER)
                    .build(),
            User
                    .builder()
                    .id(2L)
                    .username("u")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .build()
    );

    public Optional<User> findByUsername(String username) {
        return users
                .stream()
                .filter(user -> user.hasUsername(username))
                .findFirst();
    }
}
