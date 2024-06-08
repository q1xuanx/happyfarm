package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idBrand;
    @Column(nullable = false)
    private String nameBrand;
    @Column(name = "IsDelete")
    private boolean isDelete;
}
