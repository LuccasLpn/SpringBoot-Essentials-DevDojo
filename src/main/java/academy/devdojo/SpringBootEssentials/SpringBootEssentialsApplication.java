package academy.devdojo.SpringBootEssentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SpringBootEssentialsApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootEssentialsApplication.class, args);

	}
}