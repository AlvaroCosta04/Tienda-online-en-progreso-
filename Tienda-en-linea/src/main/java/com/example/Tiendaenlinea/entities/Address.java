package com.example.Tiendaenlinea.entities;

import jakarta.validation.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    protected String streetAndNumber;

    @NotBlank
    protected String city;

    @NotBlank
    protected String country;

    @NotBlank
    protected String postalCode;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected Users user; 
}
