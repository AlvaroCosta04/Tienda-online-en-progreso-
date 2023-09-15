package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Size;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query("SELECT s FROM Size s WHERE s.sizeName IN :sizeNames")
    List<Size> findBySizeNameIn(@Param("sizeNames") List<String> sizeNames);
}

