package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, String> {
    
}
