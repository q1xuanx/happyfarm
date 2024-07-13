package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
@Data
@Entity
@Getter
@Setter
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idBlog;
    @Column(columnDefinition = "LONGTEXT")
    private String details;
    private Date timeCreate;
    private String title;
    private String url;
    private String imagePresent;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users userCreate;
    @Transient
    private MultipartFile image;
}
