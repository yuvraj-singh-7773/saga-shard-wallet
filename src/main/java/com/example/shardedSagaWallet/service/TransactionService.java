package com.example.shardedSagaWallet.service;

import com.example.shardedSagaWallet.entities.Transaction;
import com.example.shardedSagaWallet.entities.TransactionStatus;
import com.example.shardedSagaWallet.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(Long fromWalletId, Long toWalletId, BigDecimal amount, String description) {
        log.info("Creating transaction from wallet {} to wallet {} with amount {} and description {}", fromWalletId, toWalletId, amount, description);

        Transaction transaction = Transaction.builder()
                .fromWalletId(fromWalletId)
                .toWalletId(toWalletId)
                .amount(amount)
                .description(description)
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created with id {}", savedTransaction.getId());
        return savedTransaction;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<Transaction> getTransactionByWalletId(Long walletId) {
        return transactionRepository.findByWalletId(walletId);
    }

    public List<Transaction> getTransactionByFromWalletId(Long fromWalletId) {
        return transactionRepository.findByFromWalletId(fromWalletId);
    }

    public List<Transaction> getTransactionByToWalletId(Long toWalletId) {
        return transactionRepository.findByToWalletId(toWalletId);
    }

    public List<Transaction> getTransactionBySagaInstanceId(Long sagaInstanceId) {
        return transactionRepository.findBySagaInstanceId(sagaInstanceId);
    }

    public List<Transaction> getTransactionByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status);
    }

    public void updateTransactionWithSagaInstanceId(Long transactionId, Long sagaInstanceId) {
        Transaction transaction = getTransactionById(transactionId);
        transaction.setSagaInstanceId(sagaInstanceId);
        transactionRepository.save(transaction);
        log.info("Transaction updated with saga instance id {}", sagaInstanceId);
    }

}
