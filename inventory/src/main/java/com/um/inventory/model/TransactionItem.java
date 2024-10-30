package com.um.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Item item;

    private int amount;

    @ManyToOne
    private Transaction transaction;
}
