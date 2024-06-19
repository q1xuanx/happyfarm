package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users, String> {
    List<Users> findByIsBanned(Boolean bool);
    List<Users> findByFullName(String name);
}
