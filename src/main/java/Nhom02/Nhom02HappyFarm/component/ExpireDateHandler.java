package Nhom02.Nhom02HappyFarm.component;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpireDateHandler {



    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpireImage(){

    }
}
