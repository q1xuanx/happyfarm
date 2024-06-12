package Nhom02.Nhom02HappyFarm.dto.request;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @NotBlank(message = "khong de rong full name")
    @Min(value = 6 , message = "it nhat 4 ky tu")
    private String fullName;

    private Date dOB;

    @NotBlank(message = "khong de rong passowrd")
    @Min(value = 6 , message = "it nhat 4 ky tu")
    private String password;

    private boolean isBanned = false;

    @NotBlank(message = "khong de rong")
    private UserRoles roles;

}
