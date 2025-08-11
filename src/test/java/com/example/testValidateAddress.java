package com.example;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestValidateAddress {
    @Test
    void TestValidateAddress_baseCase() {
        // Base case (which all other test cases rely on to be true)
        assertTrue(Person.validateAddress("32|Highland Street|Melbourne|Victoria|Australia"));
    }

    @Test
    void TestValidateAddress_testCases1() {
        // Test length of input and separators
        assertTrue(Person.validateAddress("32|Highland Street|Melbourne|Victoria|Australia"));
        assertTrue(Person.validateAddress("|||Victoria|Australia"));
        assertFalse(Person.validateAddress("||Victoria|Australia"));
        assertFalse(Person.validateAddress("||||Victoria|Australia"));
        assertFalse(Person.validateAddress("32,Highland Street,Melbourne,Victoria,Australia"));
        assertFalse(Person.validateAddress("32,Highland Street|Melbourne|Victoria|Australia"));
    }

    @Test
    void TestValidateAddress_testCases2() {
        // Test that state is always Victoria
        assertTrue(Person.validateAddress("32|Highland Street|Melbourne|Victoria|Australia"));
        assertTrue(Person.validateAddress("|||Victoria|Australia"));

        assertFalse(Person.validateAddress("32|Highland Street|Brisbane|Queensland|Australia"));
        assertFalse(Person.validateAddress("|||Queensland|Australia"));

        assertFalse(Person.validateAddress("32|Highland Street|Sydney|New South Wales|Australia"));
        assertFalse(Person.validateAddress("|||New South Wales|Australia"));
    }

    @Test
    void TestValidateAddress_testCases3() {
        // Test that country is always Australia
        assertTrue(Person.validateAddress("32|Highland Street|Melbourne|Victoria|Australia"));
        assertTrue(Person.validateAddress("|||Victoria|Australia"));

        assertFalse(Person.validateAddress("32|Highland Street|New Melbourne|Victoria|Canada"));
        assertFalse(Person.validateAddress("|||Victoria|Canada"));
    }
}
