package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Address;
import com.example.Tiendaenlinea.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.user = :user")
    Address findByUser(@Param("user") Users user);
}
