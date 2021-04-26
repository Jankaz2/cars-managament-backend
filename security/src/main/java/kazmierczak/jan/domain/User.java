package kazmierczak.jan.domain;

import kazmierczak.jan.domain.types.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    Long id;
    String username;
    String password;
    private Role role;

    /**
     * @param username username we are checking
     * @return true User's username is equal to username from param,
     * otherwise return false
     */
    public boolean hasUsername(String username) {
        return this.username.equals(username);
    }
}
