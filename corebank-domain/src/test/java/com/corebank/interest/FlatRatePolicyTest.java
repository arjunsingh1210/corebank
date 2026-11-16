package com.corebank.interest;
import com.corebank.interest.FlatRatePolicy;
import com.corebank.interest.InterestPolicy;
import com.corebank.shared.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

class FlatRatePolicyTest {

    private static final Currency EUR = Currency.getInstance("EUR");
    private final InterestPolicy policy = new FlatRatePolicy(new BigDecimal("0.12"));



    @Test
    void paysAnnualRateDividedByTwelve() {
        assertThat(policy.monthlyInterest(Money.of("1200.00", EUR)))
                .isEqualTo(Money.of("12.00", EUR));
    }

    @Test
    void zeroOrNegativeBalanceEarnsNothing() {
        assertThat(policy.monthlyInterest(Money.of("0.00", EUR))).isEqualTo(Money.of("0.00", EUR));
        assertThat(policy.monthlyInterest(Money.of("-50.00", EUR))).isEqualTo(Money.of("0.00", EUR));
    }
}