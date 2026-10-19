package com.corebank.customer;

import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final String name;
    private String email;
    private KycStatus kycStatus;

    public Customer(UUID id, String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name or email is null ");
        }
        requireValidEmail(email);

        this.id = id;
        this.name = name;
        this.email = email;
        this.kycStatus = KycStatus.PENDING;
    }

    public void verifyKyc() {
        this.kycStatus = kycStatus.VERIFIED;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void rejectKyc() {
        this.kycStatus = kycStatus.REJECTED;
    }

    public void changeEmail(String newEmail) {
        requireValidEmail(newEmail);
        this.email = newEmail;

    }

    private static void requireValidEmail(String email) {

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("email must contains @" + email);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }
}