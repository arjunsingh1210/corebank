package com.corebank.account;

import com.corebank.shared.Money;

import java.util.Currency;
import java.util.UUID;

public class SavingsAccount extends Account {

    public SavingsAccount(UUID id, UUID ownerId, Currency currency) {
        super(id, ownerId, currency);
    }

    @Override
    protected boolean canWithdraw(Money amount) {
        return !amount.isGreaterThan(getBalance());
    }
}