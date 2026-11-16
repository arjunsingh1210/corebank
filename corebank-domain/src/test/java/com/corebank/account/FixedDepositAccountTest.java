package com.corebank.account;

import com.corebank.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedDepositAccountTest {

    private static final Currency EUR = Currency.getInstance("EUR");

    private FixedDepositAccount openFixedDeposit() {
        return new FixedDepositAccount(UUID.randomUUID(), UUID.randomUUID(),
                Money.of("1000.00", EUR), LocalDate.now().plusYears(1));
    }

    @Test
    void opensWithTheInitialDeposit() {
        FixedDepositAccount account = openFixedDeposit();

        assertThat(account.getBalance()).isEqualTo(Money.of("1000.00", EUR));
    }

    @Test
    void doesNotAcceptDeposits() {
        assertThat(openFixedDeposit()).isNotInstanceOf(Depositable.class);
    }

    @Test
    void doesNotAllowWithdrawals() {
        assertThat(openFixedDeposit()).isNotInstanceOf(Withdrawable.class);
    }
}