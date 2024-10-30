package com.um.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {
    private int id;
    private String name;
    private String category;
    private String description;
    private int amount;
    private int purchasePrice;
    private int sellPrice;
    private String image;
}