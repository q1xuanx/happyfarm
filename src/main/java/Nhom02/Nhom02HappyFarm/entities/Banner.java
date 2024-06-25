package Nhom02.Nhom02HappyFarm.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
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
    private String expireDate;
    private String url;
    @Transient
    private MultipartFile image;
}
