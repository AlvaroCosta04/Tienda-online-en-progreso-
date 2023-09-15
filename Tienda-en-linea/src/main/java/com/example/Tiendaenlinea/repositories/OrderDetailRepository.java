package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Order;
import com.example.Tiendaenlinea.entities.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("SELECT od FROM OrderDetail od WHERE od.order = :order")
    List<OrderDetail> findByOrder(@Param("order") Order order);
    
    @Query("SELECT od FROM OrderDetail od WHERE od.clothes = :clothes")
    List<OrderDetail> findByClothes(@Param("clothes") Clothes clothes);
}


