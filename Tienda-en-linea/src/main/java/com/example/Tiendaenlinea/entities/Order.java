package com.example.Tiendaenlinea.entities;

import com.example.Tiendaenlinea.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected Users user;  // Relación con Users (Cliente)

    @Temporal(TemporalType.TIMESTAMP)
    protected Date orderDate;

    @Enumerated(EnumType.STRING)
    protected OrderStatus orderStatus;

    protected BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    protected Address shippingAddress;  // Relación con Address
}
