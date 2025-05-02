package co.uniquindio.setup;

import co.uniquindio.enums.UserStatus;
import co.uniquindio.model.User;
import co.uniquindio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class DefaultUserInitializer implements CommandLineRunner {
    private final DefaultUserProperties defaultUserProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            defaultUserProperties.getUsers().stream()

                    .map(this::createUser).forEach(userRepository::save);

        }
    }
    private User createUser(DefaultUserProperties.DefaultUser defaultUser){
        return  User.builder()
                .id( UUID.randomUUID().toString())
                .address(defaultUser.email())
                .role(defaultUser.role())
                .fullName(defaultUser.name())
                .password(defaultUser.password())
                .build();

    }
}
