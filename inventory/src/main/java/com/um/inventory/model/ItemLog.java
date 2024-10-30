package com.um.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class ItemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int previousAmount;
    private int currentAmount;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Change change;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;
}
