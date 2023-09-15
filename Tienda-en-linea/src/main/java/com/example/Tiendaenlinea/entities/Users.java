package com.example.Tiendaenlinea.entities;

import com.example.Tiendaenlinea.enums.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
    protected String name;

    @NotBlank
    @Size(min = 3, max = 30, message = "El apellido debe tener entre 3 y 30 caracteres")
    protected String last_name;

    @Size(min = 5, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
    protected String user_name;

    @NotBlank
    @Column(unique = true)
    protected String email;

    @NotBlank
    protected String password;

    protected String description;

    @Column(columnDefinition = "MEDIUMTEXT")
    protected String image;

    @Temporal(TemporalType.DATE)
    protected Date creationDate;

    @Enumerated(EnumType.STRING)
    protected Rol rol;
}
