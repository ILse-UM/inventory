package com.um.inventory.service;

import com.um.inventory.dto.TransactionRequestDto;
import com.um.inventory.dto.TransactionResponseDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionResponseDto createTransaction(TransactionRequestDto transaction);
    Optional<TransactionResponseDto> getTransaction(int id);
    List<TransactionResponseDto> getAllTransaction(int page, int size);
    TransactionResponseDto updateTransaction(TransactionRequestDto transactionDto, int id);
    void deleteTransaction(int id);
}
