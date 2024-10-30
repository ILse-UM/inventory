package com.um.inventory.service.impl;

import com.um.inventory.dto.*;
import com.um.inventory.model.Item;
import com.um.inventory.model.Transaction;
import com.um.inventory.model.TransactionItem;
import com.um.inventory.model.TransactionType;
import com.um.inventory.repository.ItemRepository;
import com.um.inventory.repository.TransactionItemRepository;
import com.um.inventory.repository.TransactionRepository;
import com.um.inventory.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    private ItemRepository itemRepository;

    private TransactionItemRepository transactionItemRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ItemRepository itemRepository,
                                  TransactionItemRepository transactionItemRepository) {
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.transactionItemRepository = transactionItemRepository;
    }

    @Override
    public TransactionResponseDto createTransaction(TransactionCreationDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType()));
        transaction.setDescription(transactionDto.getDescription());

        List<TransactionItem> transactionItems = new ArrayList<>();
        int totalPrice = processTransactionItems(transactionDto, transaction, transactionItems);

        // Set item dan total harga pada transaksi
        transaction.setItems(transactionItems);
        transaction.setTotalPrice(totalPrice);

        // Simpan transaksi ke repository
        transactionRepository.save(transaction);

        // Kembalikan dalam bentuk TransactionResponseDto
        return toTransactionResponseDto(transaction);
    }

    @Override
    public Optional<TransactionResponseDto> getTransaction(int id) {
        Optional<TransactionResponseDto> response = transactionRepository.findById(id).map(this::toTransactionResponseDto);
        return response;
    }

    @Override
    public List<TransactionResponseDto> getAllTransaction(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.getContent().stream().map(this::toTransactionResponseDto).toList();
    }

    @Override
    public TransactionResponseDto updateTransaction(TransactionCreationDto transactionDto, int id) {
        // Cari transaksi berdasarkan Id, atau throw tidak ditemukan
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction tidak ditemukan"));

        transaction.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType()));
        transaction.setDescription(transactionDto.getDescription());

        // Proses item transaksi dan kalkulasi total harga
        List<TransactionItem> transactionItems = new ArrayList<>();
        int totalPrice = processTransactionItems(transactionDto, transaction, transactionItems);

        transaction.setItems(transactionItems);
        transaction.setTotalPrice(totalPrice);

        // Simpan transaksi ke repository
        transactionRepository.save(transaction);

        return toTransactionResponseDto(transaction);
    }


    @Override
    public void deleteTransaction(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction tidak ditemukan"));

        transactionRepository.deleteById(id);
    }

    private int processTransactionItems(TransactionCreationDto transactionDto,
                                        Transaction transaction,
                                        List<TransactionItem> transactionItems) {
        int totalPrice = 0;
        for (TransactionItemDto itemDto : transactionDto.getItems()) {
            totalPrice += processSingleTransactionItem(transaction, transactionItems, itemDto);
        }
        return totalPrice;
    }

    private int processSingleTransactionItem(Transaction transaction,
                                             List<TransactionItem> transactionItems,
                                             TransactionItemDto itemDto) {
        // Cari item berdasarkan Id, atau throw tidak ditemukan
        Item item = itemRepository.findById(itemDto.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item tidak ditemukan"));

        // buat transaksi baru dan masukkan property
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setItem(item);
        transactionItem.setAmount(itemDto.getAmount());
        transactionItem.setTransaction(transaction);

        // Simpan item transaksi ke repository
        transactionItemRepository.save(transactionItem);

        // tambahkan item repository ke dalam transaksi
        transactionItems.add(transactionItem);

        // Kembalikan nilai subtotal
        return itemDto.getAmount() * item.getSellPrice();
    }

    private TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .transactionDate(transaction.getTransactionDate().toString())
                .transactionType(transaction.getTransactionType())
                .items(transaction.getItems().stream().map( item ->
                        TransactionItemDto.builder()
                                .item(ItemResponseDto.builder()
                                        .id(item.getId())
                                        .name(item.getItem().getName())
                                        .category(String.valueOf(item.getItem().getCategory()))
                                        .build())
                                .amount(item.getAmount())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
