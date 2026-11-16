import com.corebank.interest.InterestPolicy;
import com.corebank.interest.TieredRatePolicy;
import com.corebank.shared.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TieredRatePolicyTest {

    private static final Currency EUR = Currency.getInstance("EUR");
    private final InterestPolicy policy = new TieredRatePolicy(
            Money.of("1000.00", EUR), new BigDecimal("0.012"), new BigDecimal("0.024"));

    @Test
    void belowThresholdUsesLowerRate() {
        assertThat(policy.monthlyInterest(Money.of("600.00", EUR)))
                .isEqualTo(Money.of("0.60", EUR));
    }

    @Test
    void atThresholdUsesHigherRate() {
        assertThat(policy.monthlyInterest(Money.of("1000.00", EUR)))
                .isEqualTo(Money.of("2.00", EUR));
    }
}