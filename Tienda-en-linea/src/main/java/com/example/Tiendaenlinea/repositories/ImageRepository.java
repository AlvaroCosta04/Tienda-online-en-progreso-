package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("SELECT i FROM Image i WHERE i.id = :imageId")
    Image findImageById(@Param("imageId") Integer imageId);
}
