package Nhom02.Nhom02HappyFarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
public class Nhom02HappyFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(Nhom02HappyFarmApplication.class, args);
	}

}
