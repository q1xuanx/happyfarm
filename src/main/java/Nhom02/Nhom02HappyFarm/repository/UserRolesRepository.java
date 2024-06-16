package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, String> {
    List<UserRoles> findByIsDelete(Boolean bool);
    List<UserRoles> findByNameRoles(String name);
}
