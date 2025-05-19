package co.uniquindio;

import co.uniquindio.setup.DefaultUserProperties;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;



@EnableConfigurationProperties(DefaultUserProperties.class)
@SpringBootApplication //(scanBasePackages = "co.uniquindio")
@EnableAsync
public class ProyectoFinalProgramacionAvanzadaApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		SpringApplication.run(ProyectoFinalProgramacionAvanzadaApplication.class, args);
	}

}
