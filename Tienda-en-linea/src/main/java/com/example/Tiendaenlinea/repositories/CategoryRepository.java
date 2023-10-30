package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Category findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT c FROM Category c")
    List<Category> findAll();
}


