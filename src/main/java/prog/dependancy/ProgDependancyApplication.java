package prog.dependancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProgDependancyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProgDependancyApplication.class, args);
	}
}
