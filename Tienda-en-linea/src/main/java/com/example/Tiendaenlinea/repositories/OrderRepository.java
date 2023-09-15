package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Order;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.enums.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.user = :user")
    List<Order> findByUser(@Param("user") Users user);
    
    @Query("SELECT o FROM Order o WHERE o.orderStatus = :orderStatus")
    List<Order> findByStatus(@Param("orderStatus") OrderStatus orderStatus);
}

