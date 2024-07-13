package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeFertilizerRepository extends JpaRepository<TypeFertilizer, String> {
    List<TypeFertilizer> findByIsDelete(Boolean bool);
    List<TypeFertilizer> findByNameTypeFertilizer(String name);
}
