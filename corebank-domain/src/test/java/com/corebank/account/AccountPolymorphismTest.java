package com.corebank.account;

import com.corebank.shared.Money;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountPolymorphismTest {

    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    void eachAccountTypeAppliesItsOwnWithdrawalRule() {
        List<TransactionalAccount> accounts = List.of(
                new SavingsAccount(UUID.randomUUID(), UUID.randomUUID(), EUR),
                new CurrentAccount(UUID.randomUUID(), UUID.randomUUID(), EUR, Money.of("100.00", EUR)));

        List<Boolean> results = accounts.stream()
                .map(account -> account.canWithdraw(Money.of("50.00", EUR)))
                .toList();

        assertThat(results).containsExactly(false, true);
    }

    @Test
    void bonusCanBeCreditedToEveryDepositableAccount() {
        SavingsAccount savings = new SavingsAccount(UUID.randomUUID(), UUID.randomUUID(), EUR);
        CurrentAccount current = new CurrentAccount(UUID.randomUUID(), UUID.randomUUID(), EUR, Money.of("100.00", EUR));
        List<Depositable> accounts = List.of(savings, current);

        accounts.forEach(account -> account.deposit(Money.of("5.00", EUR)));

        assertThat(savings.getBalance()).isEqualTo(Money.of("5.00", EUR));
        assertThat(current.getBalance()).isEqualTo(Money.of("5.00", EUR));
    }
}