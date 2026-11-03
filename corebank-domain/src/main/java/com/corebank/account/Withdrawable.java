package com.corebank.account;

import com.corebank.shared.Money;

public interface Withdrawable {
    void withdraw(Money amount);
}