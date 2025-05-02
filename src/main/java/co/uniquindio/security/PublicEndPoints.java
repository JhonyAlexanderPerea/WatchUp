package co.uniquindio.security;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PublicEndPoints {
    private final RequestMatcher[] matchers = new RequestMatcher[] {
            new AntPathRequestMatcher("/auth/**", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/auth/login", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/auth/register", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/reports", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/reports/*", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/categories", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/categories/*", HttpMethod.GET.name())
    };
}
