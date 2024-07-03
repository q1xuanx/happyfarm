package Nhom02.Nhom02HappyFarm.config;

import Nhom02.Nhom02HappyFarm.service.BrandService;
import Nhom02.Nhom02HappyFarm.service.OriginService;
import Nhom02.Nhom02HappyFarm.service.TypeFertilizerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitIntoDatabase {
    private final String unknow_name = "Không có";
    @Bean
    CommandLineRunner initDatabase (OriginService origin, BrandService brandService, TypeFertilizerService typeFertilizerService){
        return args -> {
            origin.checkValidName(unknow_name);
            brandService.checkValidName(unknow_name);
            typeFertilizerService.checkValidName(unknow_name);
        };
    }
}
