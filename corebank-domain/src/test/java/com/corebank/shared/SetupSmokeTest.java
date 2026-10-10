package com.corebank.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SetupSmokeTest {

    @Test
    void buildToolsAreWiredCorrectly() {
        int result = 1 + 1;

        assertThat(result).isEqualTo(2);
    }
}