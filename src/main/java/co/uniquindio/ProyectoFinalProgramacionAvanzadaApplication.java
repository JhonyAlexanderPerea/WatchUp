package co.uniquindio;

import co.uniquindio.setup.DefaultUserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@EnableConfigurationProperties(DefaultUserProperties.class)
@SpringBootApplication //(scanBasePackages = "co.uniquindio")
@EnableAsync
public class ProyectoFinalProgramacionAvanzadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalProgramacionAvanzadaApplication.class, args);
	}

}
