package com.um.inventory.controller;

import com.um.inventory.dto.TransactionCreationDto;
import com.um.inventory.dto.TransactionResponseDto;
import com.um.inventory.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("transaction")
    public ResponseEntity<List<TransactionResponseDto>> getTransactions(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(transactionService.getAllTransaction(page, size));
    }

    @GetMapping("transaction/{id}")
    public ResponseEntity<Optional<TransactionResponseDto>> getTransactionById(@PathVariable("id") int id){
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @PostMapping("transaction/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionCreationDto transaction){
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    @PutMapping("transaction/{id}/update")
    public ResponseEntity<TransactionResponseDto> updateTransaction(@RequestBody TransactionCreationDto transactionDto, @PathVariable("id") int id){
        return new ResponseEntity<>(transactionService.updateTransaction(transactionDto, id), HttpStatus.OK);
    }

    @DeleteMapping("transaction/{id}/delete")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") int id){
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>("Transaction deleted", HttpStatus.OK);
    }
}
