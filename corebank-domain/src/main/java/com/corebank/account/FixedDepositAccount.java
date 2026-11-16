package com.corebank.account;

import com.corebank.shared.Money;

import java.time.LocalDate;
import java.util.UUID;

public class FixedDepositAccount extends Account {

    private final LocalDate maturityDate;

    public FixedDepositAccount(UUID id, UUID ownerId, Money initialDeposit, LocalDate maturityDate) {
        super(id, ownerId, initialDeposit.currency());
        credit(initialDeposit);
        this.maturityDate = maturityDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }
}