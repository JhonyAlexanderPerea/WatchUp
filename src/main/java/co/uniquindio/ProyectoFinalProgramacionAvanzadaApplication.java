package co.uniquindio;

import co.uniquindio.setup.DefaultUserProperties;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DefaultUserProperties.class)
@SpringBootApplication //(scanBasePackages = "co.uniquindio")
public class ProyectoFinalProgramacionAvanzadaApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("MONGO_DB_USERNAME", dotenv.get("MONGO_DB_USERNAME"));
		System.setProperty("MONGO_DB_PASSWORD", dotenv.get("MONGO_DB_PASSWORD"));
		SpringApplication.run(ProyectoFinalProgramacionAvanzadaApplication.class, args);
	}

}
