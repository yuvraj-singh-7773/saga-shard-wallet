package com.example.shardedSagaWallet.service.saga.steps;

import com.example.shardedSagaWallet.entities.Transaction;
import com.example.shardedSagaWallet.entities.TransactionStatus;
import com.example.shardedSagaWallet.repository.TransactionRepository;
import com.example.shardedSagaWallet.service.saga.SagaContext;
import com.example.shardedSagaWallet.service.saga.SagaStepInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateTransactionStatus implements SagaStepInterface {

    private final TransactionRepository transactionRepository;

    @Override
    public boolean execute(SagaContext context) {
        Long transactionId = context.getLong("transactionId");

        log.info("Updating transaction status for transaction {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        context.put("originalTransactionStatus", transaction.getStatus());

        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        log.info("Transaction status updated for transaction {}", transactionId);

        context.put("transactionStatusAfterUpdate", transaction.getStatus());

        log.info("Update transaction status step executed successfully");



        return true;
    }

    @Override
    public boolean compensate(SagaContext context) {
        Long trasactionId=context.getLong("transactionId");
        TransactionStatus orgTrasactionStatus =context.getString("originalTransactionStatus");

        log.info("Compensating transaction status for transaction {}", trasactionId);

        Transaction transaction=transactionRepository.findById(trasactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setStatus(orgTrasactionStatus);
        transactionRepository.save(transaction);

        log.info("Transaction status compensated for transaction {}", trasactionId);

        return true;
    }

    @Override
    public String getStepName() {
        return SagaStepFactory.SagaStepType.UPDATE_TRANSACTION_STATUS_STEP.toString();
    }
}
