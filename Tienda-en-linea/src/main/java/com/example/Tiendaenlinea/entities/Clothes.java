package com.example.Tiendaenlinea.entities;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    protected String productName;

    @NotBlank
    protected String description;

    protected BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    protected Category category;

    @ManyToMany
    @JoinTable(
            name = "clothes_colors",
            joinColumns = @JoinColumn(name = "clothes_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    protected List<Color> colors;

    @ManyToMany
    @JoinTable(
            name = "clothes_sizes",
            joinColumns = @JoinColumn(name = "clothes_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    protected List<Size> sizes;

    private ArrayList<Integer> image_id = new ArrayList<>(5);

    protected Integer stockQuantity;

    @OneToMany(mappedBy = "clothes")
    protected List<OrderDetail> orderDetails;
}
