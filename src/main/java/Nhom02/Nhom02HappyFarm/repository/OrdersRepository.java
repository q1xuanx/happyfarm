package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, String> {
}
