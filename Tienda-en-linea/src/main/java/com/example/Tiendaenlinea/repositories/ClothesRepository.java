package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Category;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Color;
import com.example.Tiendaenlinea.entities.Size;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Integer> {

    @Query("SELECT c FROM Clothes c")
    List<Clothes> findAll();

    @Query("SELECT c FROM Clothes c WHERE c.category = :category")
    List<Clothes> findByCategory(@Param("category") Category category);

    @Query("SELECT c FROM Clothes c WHERE c.colors IN :colors")
    List<Clothes> findByColorsIn(@Param("colors") List<Color> colors);

    @Query("SELECT c FROM Clothes c WHERE c.sizes IN :sizes")
    List<Clothes> findBySizesIn(@Param("sizes") List<Size> sizes);
}
