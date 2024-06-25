package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, String> {

}
