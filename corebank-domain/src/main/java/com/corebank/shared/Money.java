package com.corebank.shared;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {

    public static Money of(String amount, Currency currency) {
        return new Money(new BigDecimal(amount), currency);
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount()), currency);
    }

    public Money subtract(Money other) {
        return new Money(amount.subtract(other.amount()), currency);
    }

    public boolean isGreaterThan(Money other) {

           return amount.compareTo(other.amount()) >0;

    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }
}