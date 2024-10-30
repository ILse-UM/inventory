package com.um.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Item {
    @Id
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private int amount;
    private int purchasePrice;
    private int sellPrice;
    private String image;
}
