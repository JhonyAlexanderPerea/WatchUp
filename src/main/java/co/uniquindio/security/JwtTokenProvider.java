package co.uniquindio.security;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtEncoder encoder;

    public String generateTokenAsString(String username, Collection<String> roles,
                                        Instant issuedAt,Instant expiresAt) {
        JwtClaimsSet claims = JwtClaimsSet.builder()

                .issuer("self")
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(username)
                .claim("roles", roles)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
