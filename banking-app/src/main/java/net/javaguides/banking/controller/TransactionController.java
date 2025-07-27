package net.javaguides.banking.controller;

import net.javaguides.banking.entity.Transaction;
import net.javaguides.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/{accountId}/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccount_IdOrderByTimestampDesc(accountId);
        return ResponseEntity.ok(transactions);
    }
}
