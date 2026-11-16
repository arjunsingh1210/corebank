package com.corebank.account;

import com.corebank.shared.Money;

import java.util.Currency;
import java.util.UUID;

public abstract class TransactionalAccount extends Account implements Depositable, Withdrawable {

    protected TransactionalAccount(UUID id, UUID ownerId, Currency currency) {
        super(id, ownerId, currency);
    }

    @Override
    public final void deposit(Money amount) {
        credit(amount);
    }

    @Override
    public final void withdraw(Money amount) {
        if (amount.isPositive() && !canWithdraw(amount)) {
            throw new InsufficientFundsException(
                    "cannot withdraw " + amount + ", balance is " + getBalance());
        }
        debit(amount);
    }

    protected abstract boolean canWithdraw(Money amount);
}