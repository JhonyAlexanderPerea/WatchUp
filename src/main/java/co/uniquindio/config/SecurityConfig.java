package co.uniquindio.config;

import co.uniquindio.security.JwtAuthFilter;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Desactiva CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll()  // Permite todas las rutas
//                        .anyRequest().permitAll()  // Doble validación para compatibilidad
//                )
//                .formLogin(form -> form.disable())  // Desactiva el formulario de login
//                .httpBasic(basic -> basic.disable());  // Desactiva autenticación básica
//
//        return http.build();
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // Ruta pública
                        .anyRequest().authenticated() // Todas las demás requieren autenticación
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // 👈 Filtro JWT

        return http.build();
    }

}