package com.corebank.account;

import com.corebank.shared.InvalidAmountException;
import com.corebank.shared.Money;

import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

public class Account {

    private final UUID id;
    private final UUID ownerId;
    private Money balance;

    public Account(UUID id, UUID ownerId, Currency currency) {
        this.id = id;
        this.ownerId = ownerId;
        this.balance = Money.of("0", currency);
    }

    public void deposit(Money amount) {
        requirePositive(amount);
        balance = balance.add(amount);
    }

    public void withdraw(Money amount) {
        requirePositive(amount);
        if(amount.isGreaterThan(balance)){
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + ", balance is " + balance);
        }
        balance = balance.subtract(amount);
    }

    private void requirePositive(Money amount) {
        if(!amount.isPositive()){
            throw new InvalidAmountException("amount must be positive: " + amount);
        }

    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Money getBalance() {
        return balance;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}