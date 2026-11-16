package com.corebank.account;

import com.corebank.shared.InvalidAmountException;
import com.corebank.shared.Money;

import java.util.Currency;
import java.util.UUID;

public class CurrentAccount extends TransactionalAccount {

    private final Money overdraftLimit;

    public CurrentAccount(UUID id, UUID ownerId, Currency currency, Money overdraftLimit) {
        super(id, ownerId, currency);
        if (overdraftLimit.isNegative()) {
            throw new InvalidAmountException(
                    "overdraft limit must not be negative: " + overdraftLimit);
        }
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    protected boolean canWithdraw(Money amount) {
        Money available = getBalance().add(overdraftLimit);
        return !amount.isGreaterThan(available);
    }

    public Money getOverdraftLimit() {
        return overdraftLimit;
    }
}