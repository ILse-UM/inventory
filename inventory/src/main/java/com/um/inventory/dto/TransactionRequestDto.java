package com.um.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestDto {
    private String transactionType;

    @Builder.Default
    private List<TransactionItemRequestDto> items = new ArrayList<>();
    private String description;
}
