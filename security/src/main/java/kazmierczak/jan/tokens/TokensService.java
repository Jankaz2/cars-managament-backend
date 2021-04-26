package kazmierczak.jan.tokens;

import kazmierczak.jan.domain.User;
import kazmierczak.jan.dto.AuthenticationDataDto;
import kazmierczak.jan.dto.TokensDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokensService {
    @Value("${tokens.access-token.expiration-time-ms}")
    private Long accessTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.expiration-time-ms}")
    private Long refreshTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.property}")
    private String refreshTokenProperty;

    @Value("${tokens.prefix}")
    private String tokensPrefix;

    public TokensDto generateTokens(AuthenticationDataDto authenticationDataDto) {
        if (authenticationDataDto == null) {
            throw new IllegalArgumentException("Authentication data is null");
        }
        return null;
    }
}
