package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class OriginFertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idOrigin;

    @NotNull(message = "Không được để trống tên")
    @NotEmpty(message = "Không được để trống tên")
    private String nameOrigin;
    private boolean isDelete;
}
