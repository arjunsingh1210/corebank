package com.corebank.interest;

import com.corebank.shared.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TieredRatePolicy implements InterestPolicy {

    private final Money threshold;
    private final BigDecimal lowerAnnualRate;
    private final BigDecimal higherAnnualRate;

    public TieredRatePolicy(Money threshold, BigDecimal lowerAnnualRate, BigDecimal higherAnnualRate) {
        this.threshold = threshold;
        this.lowerAnnualRate = lowerAnnualRate;
        this.higherAnnualRate = higherAnnualRate;
    }

    @Override
    public Money monthlyInterest(Money balance) {

        // 1. if balance is not positive → zero in the balance's currency
        if(!balance.isPositive()){
            return Money.of("0", balance.currency());
        }
        // 2. pick the rate: below threshold → lower rate; at or above → higher rate
        BigDecimal annualRate;
        if(threshold.isGreaterThan(balance)){
            annualRate = lowerAnnualRate;
        }else{
            annualRate = higherAnnualRate;
        }
        // 3. monthly rate = chosen rate ÷ 12 (scale 10, HALF_EVEN)
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12),10, RoundingMode.HALF_EVEN);
        // 4. return balance multiplied by the monthly rate
        return balance.multiply(monthlyRate);
       // return null;
    }
}