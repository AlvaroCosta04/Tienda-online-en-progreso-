package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Cart;
import com.example.Tiendaenlinea.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT cart FROM Cart cart WHERE cart.user = :user")
    Cart findByUser(@Param("user") Users user);
}


