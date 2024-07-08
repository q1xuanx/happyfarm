package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    List<Users> findByIsBanned(Boolean bool);
    List<Users> findByFullName(String name);
}
