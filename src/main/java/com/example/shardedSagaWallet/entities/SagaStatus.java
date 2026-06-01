package com.example.shardedSagaWallet.entities;

public enum SagaStatus {
    STARTED,
    RUNNING,
    COMPLETED,
    FAILED,
    COMPENSATING,
    COMPENSATED,
}
