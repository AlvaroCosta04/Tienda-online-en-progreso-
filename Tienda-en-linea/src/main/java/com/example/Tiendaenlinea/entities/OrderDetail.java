package com.example.Tiendaenlinea.entities;

import java.math.BigDecimal;
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
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    protected Order order;  

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    protected Clothes clothes;  

    protected Integer quantity;

    protected BigDecimal unitPrice;

    protected BigDecimal subtotal;
}

