package com.example.shardedSagaWallet.service.saga;

public interface SagaStepInterface {
    boolean execute(SagaContext context);

    boolean compensate(SagaContext context);

    String getStepName();
}
