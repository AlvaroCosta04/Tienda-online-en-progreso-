package com.example.Tiendaenlinea.entities;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected Users user;  // Relación con Users (Cliente)

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    protected Clothes clothes;  // Relación con Ropa

    @NotBlank
    protected String text;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date commentDate;

    protected Integer rating;  // Puntuación de estrellas
}

