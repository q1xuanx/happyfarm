package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, String> {
    List<Brand> findByIsDelete(Boolean book);
    List<Brand> findByNameBrand(String name);
}
