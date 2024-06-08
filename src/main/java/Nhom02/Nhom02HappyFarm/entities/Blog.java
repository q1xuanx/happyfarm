package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
@Data
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String IdBlog;
    private String Details;
    private Date TimeCreate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUser")
    private Users UserCreate;
}
