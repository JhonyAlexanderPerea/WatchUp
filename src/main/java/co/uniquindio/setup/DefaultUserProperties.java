package co.uniquindio.setup;


import co.uniquindio.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "default-users")
@RequiredArgsConstructor(onConstructor_ = @ConstructorBinding)
public class DefaultUserProperties {
    private final List<DefaultUser> users;
    public record DefaultUser(String name, String email, String password, Role role) {}
}
