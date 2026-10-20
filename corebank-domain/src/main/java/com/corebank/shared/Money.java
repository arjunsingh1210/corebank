package com.corebank.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "amount Must not be null ");
        Objects.requireNonNull(currency, "currency must not null");
        amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }
    //amount = amount.setScale(____, RoundingMode.HALF_EVEN);

    public static Money of(String amount, Currency currency) {
        return new Money(new BigDecimal(amount), currency);
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount()), currency);
    }


    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount()), currency);
    }

    public boolean isGreaterThan(Money other) {
        requireSameCurrency(other);
           return amount.compareTo(other.amount()) >0;

    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    private void requireSameCurrency(Money other){
            if (!currency.equals(other.currency())) {
                throw new CurrencyMismatchException(
                        "Cannot combine " + other.currency() + " with " + currency);

            }
        }


}