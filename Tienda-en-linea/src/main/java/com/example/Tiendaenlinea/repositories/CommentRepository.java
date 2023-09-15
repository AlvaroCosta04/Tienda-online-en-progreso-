package com.example.Tiendaenlinea.repositories;

import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Comment;
import com.example.Tiendaenlinea.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.clothes = :clothes")
    List<Comment> findByClothes(@Param("clothes") Clothes clothes);
    
    @Query("SELECT c FROM Comment c WHERE c.user = :user")
    List<Comment> findByUser(@Param("user") Users user);
}


