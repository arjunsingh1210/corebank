package com.corebank.account;

import com.corebank.shared.Money;

public interface Depositable {
    void deposit(Money amount);
}