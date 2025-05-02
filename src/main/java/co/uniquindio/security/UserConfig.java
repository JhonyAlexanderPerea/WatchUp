package co.uniquindio.security;

import co.uniquindio.repository.UserRepository;
import co.uniquindio.dtos.common.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Configuration
public class UserConfig {
    @Bean
    public UserDetailsService userDetailsServiceFromDataBase(
            UserRepository userRepository){
        return username -> userRepository.findUserByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuario no encontrado"));

    }
}
