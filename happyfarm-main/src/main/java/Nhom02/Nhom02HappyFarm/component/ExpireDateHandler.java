package Nhom02.Nhom02HappyFarm.component;


import Nhom02.Nhom02HappyFarm.entities.Banner;
import Nhom02.Nhom02HappyFarm.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpireDateHandler {
    private final BannerRepository bannerRepository;
    //0h moi ngay se ktra va xoa
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpireImage(){
        System.out.println("Check date working");
        LocalDate localDateTime = LocalDate.now();
        List<Banner> list = bannerRepository.findAll().stream().filter(s->{
            LocalDate expDate = LocalDate.parse(s.getExpireDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return expDate.isBefore(localDateTime);
        }).toList();
        for(Banner banner : list){
            bannerRepository.delete(banner);
        }
        System.out.println("Check date finished");
    }
}
