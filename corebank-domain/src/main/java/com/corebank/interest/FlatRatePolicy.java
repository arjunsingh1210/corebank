package com.corebank.interest;

import com.corebank.shared.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FlatRatePolicy implements InterestPolicy {

    private final BigDecimal annualRate;

    public FlatRatePolicy(BigDecimal annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public Money monthlyInterest(Money balance) {
        // 1. if balance is not positive → return zero in the balance's currency
        // 2. monthly rate = annualRate ÷ 12 (divide with scale 10 and HALF_EVEN)
        // 3. return balance multiplied by the monthly rate
        //return null;
        if(!balance.isPositive()){
            return Money.of("0", balance.currency());
        }
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN);

        return balance.multiply(monthlyRate);
    }
}