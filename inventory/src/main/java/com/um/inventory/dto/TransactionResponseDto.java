package com.um.inventory.dto;

import com.um.inventory.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDto {
    private int id;
    private String transactionDate;
    private TransactionType transactionType;
    private List<TransactionItemResponseDto> items = new ArrayList<>();
    private int totalPrice;
    private String description;
}
