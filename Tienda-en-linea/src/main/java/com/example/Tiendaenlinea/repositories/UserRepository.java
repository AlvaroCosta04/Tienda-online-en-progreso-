package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.enums.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.user_name = :user_name")
    Users findByUserName(@Param("user_name") String user_name);

    @Query("SELECT u FROM Users u WHERE u.rol = :rol")
    List<Users> findByRol(@Param("rol") Rol rol);
}
