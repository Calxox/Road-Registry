package com.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.example.Person;

class TestAddDemeritPoints {
    @Test
    void testAddDemeritPoints_testCase1() {
        Person testAddDemeritPointsCase1 = new Person("01-01-2000");
        String result = testAddDemeritPointsCase1.addDemeritPoints("05-30-2025", 3);
        assertEquals("Failed", result);
    }
    
     @Test
    void testAddDemeritPoints_testCase2() {
        Person testAddDemeritPointsCase2 = new Person("01-01-2000");
        String result = testAddDemeritPointsCase2.addDemeritPoints("30-05-2025", 3.5);
        assertEquals("Failed", result);
    }

    @Test
    void testAddDemeritPoints_testCase3() {
        Person testAddDemeritPointsCase3 = new Person("01-01-2000");
        String result = testAddDemeritPointsCase3.addDemeritPoints("30-05-2025", 7);
        assertEquals("Failed", result);
    }

    @Test
    void testAddDemeritPoints_testCase4() {
        Person testAddDemeritPointsCase4 = new Person("01-01-2007");
        String result1 = testAddDemeritPointsCase4.addDemeritPoints("30-05-2025", 6);
        String result2 = testAddDemeritPointsCase4.addDemeritPoints("04-07-2025", 3);
        // total demerit points should be 9, exceeding the limit of 6 for under 21 years old
        assertEquals("Success", result1);
        assertEquals("Success", result2);
        assertTrue(testAddDemeritPointsCase4.getSuspended());
    }

    @Test
    void testAddDemeritPoints_testCase5() {
        Person testAddDemeritPointsCase5 = new Person("01-01-2000");
        String result1 = testAddDemeritPointsCase5.addDemeritPoints("30-05-2025", 6);
        String result2 = testAddDemeritPointsCase5.addDemeritPoints("04-07-2025", 4);
        // total demerit points should be 10, not exceeding the limit of 12 for over 21 years old
        assertEquals("Success", result1);
        assertEquals("Success", result2);
        assertFalse(testAddDemeritPointsCase5.getSuspended());
    }
}