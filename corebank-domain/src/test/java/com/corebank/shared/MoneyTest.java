package com.corebank.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency USD = Currency.getInstance("USD");

    @Test
    void addingTwoAmountsInSameCurrencyReturnsTheSum() {
        Money a = Money.of("10.50", EUR);
        Money b = Money.of("4.25", EUR);

        Money result = a.add(b);

        assertThat(result).isEqualTo(Money.of("14.75", EUR));
    }

    @Test
    void subtractingReturnsTheDifference() {
        Money a = Money.of("10.50", EUR);
        Money b = Money.of("4.25", EUR);

        Assertions.assertNotNull(a);
        Money result = a.subtract(b);

        assertThat(result).isEqualTo(Money.of("6.25", EUR));
    }

    @Test
    void addingDifferentCurrenciesThrows() {
        Money euros = Money.of("10.00", EUR);
        Money dollars = Money.of("10.00", USD);

        assertThatThrownBy(() -> euros.add(dollars))
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessageContaining("EUR")
                .hasMessageContaining("USD");
    }

    @Test
    void addDoesNotChangeTheOriginal() {
        Money a = Money.of("10.50", EUR);
        Money b = Money.of("4.25", EUR);

        a.add(b);

        assertThat(a).isEqualTo(Money.of("10.50", EUR));
    }

    @Test
    void amountsWithDifferentScaleAreEqual() {
        Money twoDecimals = Money.of("10.00", EUR);
        Money oneDecimal = Money.of("10.0", EUR);

        assertThat(twoDecimals).isEqualTo(oneDecimal);
    }

    @Test
    void equalMoneyHasEqualHashCode() {
        Money first = Money.of("10.00", EUR);
        Money second = Money.of("10.0", EUR);

        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    void sameAmountInDifferentCurrencyIsNotEqual() {
        assertThat(Money.of("10.00", EUR)).isNotEqualTo(Money.of("10.00", USD));
    }

    @Test
    void isGreaterThanComparesAmounts() {
        Money ten = Money.of("10.00", EUR);
        Money five = Money.of("5.00", EUR);

        assertThat(ten.isGreaterThan(five)).isTrue();
        assertThat(five.isGreaterThan(ten)).isFalse();
        assertThat(ten.isGreaterThan(Money.of("10.0", EUR))).isFalse();
    }

    @Test
    void isNegativeIsTrueOnlyBelowZero() {
        assertThat(Money.of("-0.01", EUR).isNegative()).isTrue();
        assertThat(Money.of("0.00", EUR).isNegative()).isFalse();
        assertThat(Money.of("5.00", EUR).isNegative()).isFalse();
    }

    @Test
    void isPositiveIsTrueOnlyAboveZero() {
        assertThat(Money.of("0.01", EUR).isPositive()).isTrue();
        assertThat(Money.of("0.00", EUR).isPositive()).isFalse();
        assertThat(Money.of("-5.00", EUR).isPositive()).isFalse();
    }
}