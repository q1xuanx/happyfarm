package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idRatings;
    private String comments;
    @Min(1)
    @Max(5)
    private int points;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users idUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFertilizer")
    private Fertilizer idFertilizer;
}
