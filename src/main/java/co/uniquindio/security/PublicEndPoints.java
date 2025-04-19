package co.uniquindio.security;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PublicEndPoints {
    private final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/api/v1/users/**",
            "/api/v1/categories/**",
            "/api/v1/notifications/**"
    };
}
