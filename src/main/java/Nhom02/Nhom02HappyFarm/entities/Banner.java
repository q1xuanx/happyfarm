package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Data
@Getter
@Setter
public class Banner {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String id;
    private String imageFile;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date expireDate;
    private String url;
}
