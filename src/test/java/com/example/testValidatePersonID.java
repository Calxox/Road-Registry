package com.example;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestValidatePersonID {
    @Test
    void testValidatePersonID_baseCase() {
        // Base case (which all other test cases rely on to be true)
        assertTrue(Person.validatePersonID("22__aaaaAA"));
    }

    @Test
    void testValidatePersonID_testCases1() {
        // Test different digits at start
        // The first two characters of personID must be digits between 2 and 9 (inclusive) 
        assertFalse(Person.validatePersonID("00__aaaaAA"));
        assertFalse(Person.validatePersonID("11__aaaaAA"));
        assertTrue(Person.validatePersonID("22__aaaaAA"));
        assertTrue(Person.validatePersonID("33__aaaaAA"));
        assertTrue(Person.validatePersonID("44__aaaaAA"));
        assertTrue(Person.validatePersonID("55__aaaaAA"));
        assertTrue(Person.validatePersonID("66__aaaaAA"));
        assertTrue(Person.validatePersonID("77__aaaaAA"));
        assertTrue(Person.validatePersonID("88__aaaaAA"));
        assertTrue(Person.validatePersonID("99__aaaaAA"));
    }

    @Test
    void testValidatePersonID_testCases2() {
        // Test different special character positions
        // There must be at least two special characters between positions 3 and 8 (inclusive)
        assertTrue(Person.validatePersonID("22__aaaaAA"));
        assertTrue(Person.validatePersonID("22a__aaaAA"));
        assertTrue(Person.validatePersonID("22aa__aaAA"));
        assertTrue(Person.validatePersonID("22aaa__aAA"));
        assertTrue(Person.validatePersonID("22aaaa__AA"));
        assertTrue(Person.validatePersonID("22_aaaa_AA"));
    }

    @Test
    void testValidatePersonID_testCases3() {
        // Test different amounts of special characters
        // There must be at least two special characters
        assertFalse(Person.validatePersonID("22aaaaaaAA"));
        assertFalse(Person.validatePersonID("22_aaaaaAA"));
        assertTrue(Person.validatePersonID("22__aaaaAA"));
        assertTrue(Person.validatePersonID("22___aaaAA"));
        assertTrue(Person.validatePersonID("22____aaAA"));
        assertTrue(Person.validatePersonID("22_____aAA"));
        assertTrue(Person.validatePersonID("22______AA"));
    }

    @Test
    void testValidatePersonID_testCases4() {
        // Test different special characters
        assertTrue(Person.validatePersonID("22!!!!!!AA"));
        assertTrue(Person.validatePersonID("22??????AA"));
        assertTrue(Person.validatePersonID("22......AA"));
        assertTrue(Person.validatePersonID("22@@@@@@AA"));
        assertTrue(Person.validatePersonID("22######AA"));
        assertTrue(Person.validatePersonID("22$$$$$$AA"));
        assertTrue(Person.validatePersonID("22%%%%%%AA"));
        assertTrue(Person.validatePersonID("22^^^^^^AA"));
        assertTrue(Person.validatePersonID("22&&&&&&AA"));
        assertTrue(Person.validatePersonID("22******AA"));
        assertTrue(Person.validatePersonID("22((((((AA"));
        assertTrue(Person.validatePersonID("22))))))AA"));
        assertTrue(Person.validatePersonID("22------AA"));
        assertTrue(Person.validatePersonID("22______AA"));
        assertTrue(Person.validatePersonID("22++++++AA"));
        assertTrue(Person.validatePersonID("22======AA"));
        assertTrue(Person.validatePersonID("22::::::AA"));
        assertTrue(Person.validatePersonID("22;;;;;;AA"));
        assertTrue(Person.validatePersonID("22~~~~~~AA"));

        assertFalse(Person.validatePersonID("22<<<<<<AA"));
        assertFalse(Person.validatePersonID("22>>>>>>AA"));
        assertFalse(Person.validatePersonID("22//////AA"));
        assertFalse(Person.validatePersonID("22\\\\\\\\\\\\AA"));
        assertFalse(Person.validatePersonID("22''''''AA"));
        assertFalse(Person.validatePersonID("22\"\"\"\"\"\"AA"));
        assertFalse(Person.validatePersonID("22[[[[[[AA"));
        assertFalse(Person.validatePersonID("22]]]]]]AA"));
        assertFalse(Person.validatePersonID("22}}}}}}AA"));
        assertFalse(Person.validatePersonID("22{{{{{{AA"));
    }

    @Test
    void testValidatePersonID_testCases5() {
        // Test different string lengths
        assertFalse(Person.validatePersonID("22__AA"));
        assertFalse(Person.validatePersonID("22__aAA"));
        assertFalse(Person.validatePersonID("22__aaAA"));
        assertFalse(Person.validatePersonID("22__aaaAA"));
        assertTrue(Person.validatePersonID("22__aaaaAA"));
        assertFalse(Person.validatePersonID("22__aaaaaAA"));
        assertFalse(Person.validatePersonID("22__aaaaaaAA"));
        assertFalse(Person.validatePersonID("22__aaaaaaaAA"));
    }

    @Test
    void testValidatePersonID_testCases6() {
        // Test uppercase letters
        // The last two characters should be uppercase letters
        assertTrue(Person.validatePersonID("22__aaaaAB"));
        assertTrue(Person.validatePersonID("22__aaaaCD"));
        assertTrue(Person.validatePersonID("22__aaaaEF"));
        assertTrue(Person.validatePersonID("22__aaaaGH"));
        assertTrue(Person.validatePersonID("22__aaaaIJ"));
        assertTrue(Person.validatePersonID("22__aaaaKL"));
        assertTrue(Person.validatePersonID("22__aaaaMN"));
        assertTrue(Person.validatePersonID("22__aaaaOP"));
        assertTrue(Person.validatePersonID("22__aaaaQR"));
        assertTrue(Person.validatePersonID("22__aaaaST"));
        assertTrue(Person.validatePersonID("22__aaaaUV"));
        assertTrue(Person.validatePersonID("22__aaaaWX"));
        assertTrue(Person.validatePersonID("22__aaaaYZ"));

        assertFalse(Person.validatePersonID("22__aaaaaa"));
        assertFalse(Person.validatePersonID("22__aaaa__"));
        assertFalse(Person.validatePersonID("22__aaaa00"));
    }
}