package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Entity
@Getter
@Setter
public class TypeFertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idTypeFertilizer;
    @Column(name = "nameTypeFertilizer", nullable = false)
    private String nameTypeFertilizer;
    private boolean isDelete;
}
