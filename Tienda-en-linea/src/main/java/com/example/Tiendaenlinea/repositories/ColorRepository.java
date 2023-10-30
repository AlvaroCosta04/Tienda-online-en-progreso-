package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Color;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query("SELECT c FROM Color c WHERE c.colorName IN :colorNames")
    List<Color> findByColorNameIn(@Param("colorNames") List<String> colorNames);
    
    @Query("SELECT c FROM Color c")
    List<Color> findAll();
}


