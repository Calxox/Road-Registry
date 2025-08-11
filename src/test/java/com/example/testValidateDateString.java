package com.example;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestValidateDateString {
    @Test
    void TestValidateDateString_baseCase() {
        // Base case (which all other test cases rely on to be true)
        assertTrue(Person.validateDateString("01-01-2000"));
    }

    @Test
    void TestValidateDateString_testCases1() {
        // Test days
        assertTrue(Person.validateDateString("01-01-2000"));
        assertTrue(Person.validateDateString("13-01-2000"));
        assertTrue(Person.validateDateString("31-01-2000"));
        assertTrue(Person.validateDateString("28-02-2000"));
        assertFalse(Person.validateDateString("31-02-2000"));
    }

    @Test
    void TestValidateDateString_testCases2() {
        // Test months
        assertTrue(Person.validateDateString("01-01-2000"));
        assertTrue(Person.validateDateString("01-12-2000"));
        assertFalse(Person.validateDateString("01-00-2000"));
        assertFalse(Person.validateDateString("01-13-2000"));
    }

    @Test
    void TestValidateDateString_testCases3() {
        // Test years
        assertTrue(Person.validateDateString("01-01-2000"));
        assertTrue(Person.validateDateString("01-01-1970"));
        assertTrue(Person.validateDateString("01-01-2037"));
        assertTrue(Person.validateDateString("01-01-3000"));
    }

    @Test
    void TestValidateDateString_testCases4() {
        // Test incorrect formats
        assertTrue(Person.validateDateString("01-01-2000"));
        assertFalse(Person.validateDateString("2000-01-01"));
    }
}
