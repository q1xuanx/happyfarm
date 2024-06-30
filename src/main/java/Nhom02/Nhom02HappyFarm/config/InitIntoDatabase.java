package Nhom02.Nhom02HappyFarm.config;

import Nhom02.Nhom02HappyFarm.service.OriginService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitIntoDatabase {
    @Bean
    CommandLineRunner initDatabase (OriginService origin){
        return args -> {
            origin.checkValidName("Không có");
        };
    }
}
