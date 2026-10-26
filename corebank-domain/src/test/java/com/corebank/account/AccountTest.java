package com.corebank.account;

import com.corebank.shared.CurrencyMismatchException;
import com.corebank.shared.InvalidAmountException;
import com.corebank.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency USD = Currency.getInstance("USD");

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(UUID.randomUUID(), UUID.randomUUID(), EUR);
    }

    private static Money eur(String amount) {
        return Money.of(amount, EUR);
    }

    @Test
    void newAccountStartsWithZeroBalance() {
        assertThat(account.getBalance()).isEqualTo(eur("0.00"));
    }

    @Test
    void depositIncreasesBalance() {
        account.deposit(eur("100.00"));

        assertThat(account.getBalance()).isEqualTo(eur("100.00"));
    }

    @Test
    void withdrawDecreasesBalance() {
        account.deposit(eur("100.00"));

        account.withdraw(eur("30.00"));

        assertThat(account.getBalance()).isEqualTo(eur("70.00"));
    }

    @Test
    void withdrawingTheExactBalanceLeavesZero() {
        account.deposit(eur("50.00"));

        account.withdraw(eur("50.00"));

        assertThat(account.getBalance()).isEqualTo(eur("0.00"));
    }

    @Test
    void withdrawingMoreThanBalanceThrowsAndLeavesBalanceUnchanged() {
        account.deposit(eur("50.00"));

        assertThatThrownBy(() -> account.withdraw(eur("50.01")))
                .isInstanceOf(InsufficientFundsException.class);
        assertThat(account.getBalance()).isEqualTo(eur("50.00"));
    }

    @Test
    void depositingZeroIsRejected() {
        assertThatThrownBy(() -> account.deposit(eur("0.00")))
                .isInstanceOf(InvalidAmountException.class);
        assertThat(account.getBalance()).isEqualTo(eur("0.00"));
    }

    @Test
    void depositingNegativeAmountIsRejected() {
        assertThatThrownBy(() -> account.deposit(eur("-10.00")))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void withdrawingNegativeAmountIsRejected() {
        account.deposit(eur("50.00"));

        assertThatThrownBy(() -> account.withdraw(eur("-10.00")))
                .isInstanceOf(InvalidAmountException.class);
        assertThat(account.getBalance()).isEqualTo(eur("50.00"));
    }

    @Test
    void depositingDifferentCurrencyIsRejected() {
        assertThatThrownBy(() -> account.deposit(Money.of("10.00", USD)))
                .isInstanceOf(CurrencyMismatchException.class);
    }

    @Test
    void accountsWithSameIdAreEqual() {
        UUID id = UUID.randomUUID();
        Account first = new Account(id, UUID.randomUUID(), EUR);
        Account second = new Account(id, UUID.randomUUID(), EUR);

        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }
}