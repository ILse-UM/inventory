package com.um.inventory.dto;

import com.um.inventory.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionItemDto {
    private ItemResponseDto item;
    private int amount;
}
