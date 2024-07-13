package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OriginRepository extends JpaRepository<OriginFertilizer, String> {
    List<OriginFertilizer> findByisDelete(Boolean bool);
    List<OriginFertilizer> findByNameOrigin(String name);
}
