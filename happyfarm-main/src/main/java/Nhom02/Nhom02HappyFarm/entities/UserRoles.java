package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idRoles;
    private String nameRoles;
    private boolean isDelete;


}
