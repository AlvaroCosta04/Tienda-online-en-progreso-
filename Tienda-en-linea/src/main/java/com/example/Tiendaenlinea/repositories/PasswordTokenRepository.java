package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.PasswordToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Integer> {

    List<PasswordToken> findAll();

    @Query("SELECT pt FROM PasswordToken pt WHERE pt.code = :code")
    Optional<PasswordToken> findByCode(@Param("code") String code);

    @Query("SELECT pt FROM PasswordToken pt WHERE pt.userMail = :userMail")
    Optional<PasswordToken> findByUserMail(@Param("userMail") String userMail);

    List<PasswordToken> findTokensByUserMail(String userMail);
}
