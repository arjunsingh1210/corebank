package com.corebank.account;

import com.corebank.shared.InvalidAmountException;
import com.corebank.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CurrentAccountTest {

    private static final Currency EUR = Currency.getInstance("EUR");

    private CurrentAccount account;

    @BeforeEach
    void setUp() {
        account = new CurrentAccount(UUID.randomUUID(), UUID.randomUUID(), EUR, eur("100.00"));
    }

    private static Money eur(String amount) {
        return Money.of(amount, EUR);
    }

    @Test
    void canWithdrawIntoOverdraftUpToTheLimit() {
        account.withdraw(eur("100.00"));

        assertThat(account.getBalance()).isEqualTo(eur("-100.00"));
    }

    @Test
    void withdrawingBeyondOverdraftLimitThrowsAndLeavesBalanceUnchanged() {
        assertThatThrownBy(() -> account.withdraw(eur("100.01")))
                .isInstanceOf(InsufficientFundsException.class);
        assertThat(account.getBalance()).isEqualTo(eur("0.00"));
    }

    @Test
    void overdraftLimitCountsOnTopOfPositiveBalance() {
        account.deposit(eur("50.00"));

        account.withdraw(eur("150.00"));

        assertThat(account.getBalance()).isEqualTo(eur("-100.00"));
    }

    @Test
    void depositReducesTheOverdraft() {
        account.withdraw(eur("60.00"));

        account.deposit(eur("40.00"));

        assertThat(account.getBalance()).isEqualTo(eur("-20.00"));
    }

    @Test
    void negativeOverdraftLimitIsRejected() {
        assertThatThrownBy(() ->
                new CurrentAccount(UUID.randomUUID(), UUID.randomUUID(), EUR, eur("-1.00")))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void stillRejectsNonPositiveAmounts() {
        assertThatThrownBy(() -> account.withdraw(eur("0.00")))
                .isInstanceOf(InvalidAmountException.class);
    }
}