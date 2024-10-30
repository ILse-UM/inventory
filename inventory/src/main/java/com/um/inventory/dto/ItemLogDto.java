package com.um.inventory.dto;

import com.um.inventory.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemLogDto {
    // TODO create service?
    private int id;
    private Item item;
    private int previousAmount;
    private int currentAmount;
    private LocalDateTime createdDate;
    private String change;
    private String actionType;
}
