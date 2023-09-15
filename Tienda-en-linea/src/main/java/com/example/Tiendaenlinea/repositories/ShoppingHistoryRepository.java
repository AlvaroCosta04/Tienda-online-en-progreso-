package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.ShoppingHistory;
import com.example.Tiendaenlinea.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingHistoryRepository extends JpaRepository<ShoppingHistory, Integer> {

    @Query("SELECT shoppingHistory FROM ShoppingHistory shoppingHistory WHERE shoppingHistory.user = :user")
    ShoppingHistory findByUser(@Param("user") Users user);
}
