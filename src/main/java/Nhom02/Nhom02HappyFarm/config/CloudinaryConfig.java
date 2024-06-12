package Nhom02.Nhom02HappyFarm.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("cloud_name", "djsjo32mt");
        configs.put("api_key", "889185168248424");
        configs.put("api_secret","gSQ1MpoEMzRjhrwQdecYPXawo4Y");
        configs.put("secure", true);
        return new Cloudinary(configs);
    }
}
