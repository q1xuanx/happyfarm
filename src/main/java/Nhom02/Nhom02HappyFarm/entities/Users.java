package Nhom02.Nhom02HappyFarm.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUser;

    private String username;

    private String fullName;

    private String email;

    @Column(name = "DateOfBirth")
    private String dob;

    private String password;

    private boolean isBanned;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRoles")
    private UserRoles roles;
}
