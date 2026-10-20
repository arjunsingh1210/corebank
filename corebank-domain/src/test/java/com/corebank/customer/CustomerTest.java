package com.corebank.customer;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    void newCustomerStartsWithPendingKyc() {
        Customer customer = new Customer(UUID.randomUUID(), "Arjun Singh", "arjun@example.com");

        assertThat(customer.getKycStatus()).isEqualTo(KycStatus.PENDING);
    }

    @Test
    void verifyingKycChangesStatusToVerified() {
        Customer customer = new Customer(UUID.randomUUID(), "Arjun Singh", "arjun@example.com");

        customer.verifyKyc();

        assertThat(customer.getKycStatus()).isEqualTo(KycStatus.VERIFIED);
    }

    @Test
    void customersWithSameIdAreEqualEvenIfOtherFieldsDiffer() {
        UUID id = UUID.randomUUID();
        Customer original = new Customer(id, "Arjun Singh", "arjun@example.com");
        Customer renamed = new Customer(id, "A. Singh", "other@example.com");

        assertThat(original).isEqualTo(renamed);
        assertThat(original.hashCode()).isEqualTo(renamed.hashCode());
    }

    @Test
    void customersWithDifferentIdsAreNotEqualEvenWithSameDetails() {
        Customer first = new Customer(UUID.randomUUID(), "Arjun Singh", "arjun@example.com");
        Customer second = new Customer(UUID.randomUUID(), "Arjun Singh", "arjun@example.com");

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void changingEmailKeepsTheSameIdentity() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Arjun Singh", "arjun@example.com");
        Customer sameCustomer = new Customer(id, "Arjun Singh", "arjun@example.com");

        customer.changeEmail("new@example.com");

        assertThat(customer.getEmail()).isEqualTo("new@example.com");
        assertThat(customer).isEqualTo(sameCustomer);
    }

    @Test
    void blankNameIsRejected() {
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), "  ", "arjun@example.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emailWithoutAtSignIsRejected() {
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), "Arjun Singh", "not-an-email"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changingToInvalidEmailIsRejected() {
        Customer customer = new Customer(UUID.randomUUID(), "Arjun Singh", "arjun@example.com");

        assertThatThrownBy(() -> customer.changeEmail("broken"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(customer.getEmail()).isEqualTo("arjun@example.com");
    }
}