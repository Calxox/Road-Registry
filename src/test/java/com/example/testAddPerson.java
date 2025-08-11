package com.example;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.nio.file.*;

class TestAddPerson {

    @BeforeAll
    private static void setUp() throws Exception {
        Path personFile = Paths.get("Person.txt");
        if (Files.exists(personFile)) {
            Files.delete(personFile);
        }
    }

    @Test // Valid input
    void testAddPerson_testCase1() {
        Person addPersonCase1 = new Person("22__aaaaAA", "Person", "One","1|Test St|Kew|Victoria|Australia","21-01-2000");
        boolean result = addPersonCase1.addPerson();
        assertTrue(result);
    }
    
    @Test // Invalid input - Invalid personID (too long)
    void testAddPerson_testCase2() {
        Person addPersonCase2 = new Person("33__bbbbbbbbbbbbBB", "Person", "Two", "2|Test St|Kew|Victoria|Australia", "22-01-2000");
        boolean result = addPersonCase2.addPerson();
        assertFalse(result);

    }

    @Test // invalid address format - wrong structure
    void testAddPerson_testCase3() {
        Person addPersonCase3 = new Person("44__ccccCC", "Person", "Three", "3 Test St|Kew|Victoria|3000|Australia", "23-01-2000");
        boolean result = addPersonCase3.addPerson();
        assertFalse(result);
    }

    @Test // invalid input - invalid date format (MM-DD-YYYY)
    void testAddPerson_testCase4() {
        Person addPersonCase4 = new Person("55__ddddDD", "Person", "Four", "4|Test St|Kew|Victoria|Australia", "01-24-2000");
        boolean result = addPersonCase4.addPerson();
        assertFalse(result);
    }

    @Test // invalid input - address is not in Victoria
    void testAddPerson_testCase5() {
        Person addPersonCase5 = new Person("66__eeeeEE", "Person", "Five", "5|Test St|Kew|Queensland|Australia", "25-01-2000");
        boolean result = addPersonCase5.addPerson();
        assertFalse(result);
    }
}