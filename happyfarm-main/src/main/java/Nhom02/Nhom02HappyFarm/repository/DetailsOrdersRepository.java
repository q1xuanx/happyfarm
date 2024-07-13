package Nhom02.Nhom02HappyFarm.repository;


import Nhom02.Nhom02HappyFarm.entities.DetailsOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsOrdersRepository extends JpaRepository<DetailsOrders, String> {
}
