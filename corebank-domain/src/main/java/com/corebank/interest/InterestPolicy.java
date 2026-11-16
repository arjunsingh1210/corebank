package com.corebank.interest;

import com.corebank.shared.Money;

public interface InterestPolicy {
    Money monthlyInterest(Money balance);
}