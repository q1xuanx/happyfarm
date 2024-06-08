package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class TypeFertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String IdTypeFertilizer;
    @Column(name = "NameTypeFertilizer", nullable = false)
    private String NameTypeFertilizer;
    private boolean IsDelete;
}
