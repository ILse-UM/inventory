package com.um.inventory.service.impl;

import com.um.inventory.dto.*;
import com.um.inventory.model.*;
import com.um.inventory.repository.ItemLogRepository;
import com.um.inventory.repository.ItemRepository;
import com.um.inventory.repository.TransactionItemRepository;
import com.um.inventory.repository.TransactionRepository;
import com.um.inventory.service.TransactionService;
import com.um.inventory.util.ImageUtil;
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

    private final TransactionRepository transactionRepository;
    private final ItemRepository itemRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final ItemLogRepository itemLogRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ItemRepository itemRepository,
                                  TransactionItemRepository transactionItemRepository,
                                  ItemLogRepository itemLogRepository) {
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.transactionItemRepository = transactionItemRepository;
        this.itemLogRepository = itemLogRepository;
    }

    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType()));
        transaction.setDescription(transactionDto.getDescription());

        // Simpan transaksi utama terlebih dahulu
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Proses item transaksi dan kalkulasi total harga
        List<TransactionItem> transactionItems = new ArrayList<>();
        int totalPrice = processTransactionItems(transactionDto, savedTransaction, transactionItems);

        // Kosongkan koleksi item terlebih dahulu, lalu tambahkan item baru
        savedTransaction.getItems().clear();
        savedTransaction.getItems().addAll(transactionItems);
        savedTransaction.setTotalPrice(totalPrice);

        // Simpan ulang transaksi dengan item dan total harga yang sudah di-update
        transactionRepository.save(savedTransaction);

        // Update jumlah item sesuai dengan transaksi
        updateItemAmounts(transactionDto);

        return toTransactionResponseDto(savedTransaction);
    }

    @Override
    public Optional<TransactionResponseDto> getTransaction(int id) {
        return transactionRepository.findById(id).map(this::toTransactionResponseDto);
    }

    @Override
    public List<TransactionResponseDto> getAllTransaction(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.getContent().stream().map(this::toTransactionResponseDto).collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDto updateTransaction(TransactionRequestDto transactionDto, int id) {
        // Cari transaksi berdasarkan Id, atau throw tidak ditemukan
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction tidak ditemukan"));

        transaction.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType()));
        transaction.setDescription(transactionDto.getDescription());

        // Proses item transaksi dan kalkulasi total harga
        List<TransactionItem> transactionItems = new ArrayList<>();
        int totalPrice = processTransactionItems(transactionDto, transaction, transactionItems);

        // Kosongkan koleksi item terlebih dahulu, lalu tambahkan item baru
        transaction.getItems().clear();
        transaction.getItems().addAll(transactionItems);
        transaction.setTotalPrice(totalPrice);

        // Simpan transaksi ke repository
        transactionRepository.save(transaction);

        // Update jumlah item sesuai dengan transaksi
        updateItemAmounts(transactionDto);

        return toTransactionResponseDto(transaction);
    }

    @Override
    public void deleteTransaction(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction tidak ditemukan"));
        transactionRepository.deleteById(id);
    }

    private int processTransactionItems(TransactionRequestDto transactionDto,
                                        Transaction transaction,
                                        List<TransactionItem> transactionItems) {
        int totalPrice = 0;
        for (TransactionItemRequestDto itemDto : transactionDto.getItems()) {
            totalPrice += processSingleTransactionItem(transaction, transactionItems, itemDto);
        }
        return totalPrice;
    }

    private int processSingleTransactionItem(Transaction transaction,
                                             List<TransactionItem> transactionItems,
                                             TransactionItemRequestDto itemDto) {
        // Cari item berdasarkan Id, atau throw tidak ditemukan
        Item item = itemRepository.findById(itemDto.getId())
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

    private void updateItemAmounts(TransactionRequestDto transactionDto) {
        for (TransactionItemRequestDto itemDto : transactionDto.getItems()) {
            Item item = itemRepository.findById(itemDto.getId())
                    .orElseThrow(() -> new RuntimeException("Item tidak ditemukan"));

            int previousAmount = item.getAmount();
            int changeAmount = itemDto.getAmount();
            int newAmount;

            // Perbarui jumlah item berdasarkan tipe transaksi
            if (transactionDto.getTransactionType().equals("PURCHASE")) {
                newAmount = previousAmount + changeAmount;
            } else if (transactionDto.getTransactionType().equals("SALE")) {
                newAmount = previousAmount - changeAmount;
            } else {
                throw new RuntimeException("Tipe transaksi tidak valid");
            }

            // Simpan log perubahan
            ItemLog log = new ItemLog();
            log.setItem(item);
            log.setPreviousAmount(previousAmount);
            log.setCurrentAmount(newAmount);
            log.setChange(transactionDto.getTransactionType().equals("PURCHASE") ? Change.PLUS : Change.MINUS);
            log.setActionType(ActionType.UPDATE);
            itemLogRepository.save(log);

            // Update jumlah item dan simpan
            item.setAmount(newAmount);
            itemRepository.save(item);
        }
    }


    private TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate().toString())
                .transactionType(transaction.getTransactionType())
                .items(transaction.getItems().stream().map(item ->
                                TransactionItemResponseDto.builder()
                                        .item(ItemResponseDto.builder()
                                                .id(item.getItem().getId())
                                                .barcode(item.getItem().getBarcode())
                                                .name(item.getItem().getName())
                                                .category(String.valueOf(item.getItem().getCategory()))
                                                .description(item.getItem().getDescription())
                                                .amount(item.getItem().getAmount())
                                                .purchasePrice(item.getItem().getPurchasePrice())
                                                .sellPrice(item.getItem().getSellPrice())
                                                .imageBase64(ImageUtil.encodeToBase64(item.getItem().getImage()))
                                                .build())
                                        .amount(item.getAmount())
                                        .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
