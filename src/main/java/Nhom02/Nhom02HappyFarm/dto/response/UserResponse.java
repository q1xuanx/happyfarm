package Nhom02.Nhom02HappyFarm.dto.response;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {
    String idUser;
    String fullName;
    Date dOB;
    String password;
    boolean isBanned;
    UserRoles roles;
}
