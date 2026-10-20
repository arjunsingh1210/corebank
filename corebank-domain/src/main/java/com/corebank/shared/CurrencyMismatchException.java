package com.corebank.shared;

public class CurrencyMismatchException extends BankingException {

    public CurrencyMismatchException(String message) {
        super(message);
    }
}