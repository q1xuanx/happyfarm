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
    private String IdRatings;
    private String Comments;
    @Min(1)
    @Max(5)
    private int Points;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUser")
    private Users IdUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdFertilizer")
    private Fertilizer IdFertilizer;
}
