package Nhom02.Nhom02HappyFarm.repository;

import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
    @Query("SELECT MONTH(o.receiveDate), SUM(o.totalAmont), COUNT(o.idOrders) " +
            "FROM Orders o " +
            "WHERE YEAR(o.receiveDate) = :year " +
            "GROUP BY MONTH(o.receiveDate)")
    List<Object[]> getMonthlyStatistics(@Param("year") int year);

    @Query("SELECT o " +
            "FROM Orders o " +
            "WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month AND LOWER(TRIM(o.statusOrders)) = LOWER(TRIM(:status))")
    List<Orders> findOrdersByMonthAndYear(@Param("year") int year, @Param("month") int month, @Param("status") String status);
}
