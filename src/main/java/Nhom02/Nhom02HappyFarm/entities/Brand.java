package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String IdBrand;
    @Column(nullable = false)
    private String NameBrand;
    @Column(name = "IsDelete")
    private boolean IsDelete;
}
