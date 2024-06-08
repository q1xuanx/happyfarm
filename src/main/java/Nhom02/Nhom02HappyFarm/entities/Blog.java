package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
@Data
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idBlog;
    private String details;
    private Date timeCreate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users userCreate;
}
