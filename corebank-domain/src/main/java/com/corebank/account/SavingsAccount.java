package com.corebank.account;

import com.corebank.interest.InterestPolicy;
import com.corebank.shared.Money;

import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

public class SavingsAccount extends TransactionalAccount implements InterestBearing {

    private static final InterestPolicy NO_INTEREST =
            balance -> Money.of("0", balance.currency());

    private final InterestPolicy interestPolicy;

    public SavingsAccount(UUID id, UUID ownerId, Currency currency) {
        this(id, ownerId, currency, NO_INTEREST);
    }

    public SavingsAccount(UUID id, UUID ownerId, Currency currency, InterestPolicy interestPolicy) {
        super(id, ownerId, currency);
        this.interestPolicy = Objects.requireNonNull(interestPolicy, "interest policy must not be null");
    }

    @Override
    protected boolean canWithdraw(Money amount) {
        return !amount.isGreaterThan(getBalance());
    }

    @Override
    public void applyMonthlyInterest() {
        Money interest = interestPolicy.monthlyInterest(getBalance());
        if (interest.isPositive()) {
            credit(interest);
        }
    }
}