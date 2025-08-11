package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.nio.file.*;

class TestUpdatePersonalDetails {

    @BeforeAll
    private static void setUp() throws Exception {
        Path personFile = Paths.get("Person.txt");
        if (Files.exists(personFile)) {
            Files.delete(personFile);
        }
    }

    @Test
    void testUpdatePersonalDetails_testCase1() {
        // Test Case 1: Valid inputs, update ID
        // Create and add person
        Person testPerson1 = new Person("01-01-2000");
        testPerson1.addPerson();

        // Update person
        Boolean result = testPerson1.updatePersonalDetails(
            "77&&bbbbBB", 
            "Tested", 
            testPerson1.getLastName(),
            testPerson1.getAddress(), 
            testPerson1.getBirthDate()
        );

        // Assuming updatePersonalDetails() returns true on success
        assertTrue(result);
    }

    @Test
    void testUpdatePersonalDetails_testCase2() {
        // Test Case 2: Person Under 18 attempts to change address
        // Create and add person
        Person testPerson2 = new Person("01-01-2009");
        testPerson2.addPerson();

        // Update person
        Boolean result = testPerson2.updatePersonalDetails(
            testPerson2.getPersonID(),
            testPerson2.getFirstName(),
            testPerson2.getLastName(),
            "2|Case St|Hawthorn|Victoria|Australia",
            testPerson2.getBirthDate());

        // Assuming updatePersonalDetails() returns false on failure
        assertFalse(result);
    }

    @Test
    void testUpdatePersonalDetails_testCase3() {
        // Test Case 3: Person with even first Digit in ID attempts to change ID
        // Create and add person
        Person testPerson3 = new Person(
            "89_aaaaAA",
            "Test",
            "User",
            "1|Test St|Kew|Victoria|Australia",
            "01-01-2000"
        );
        testPerson3.addPerson();

        // Update person
        Boolean result = testPerson3.updatePersonalDetails(
            "55__aaaaAA",
            testPerson3.getFirstName(),
            testPerson3.getLastName(),
            testPerson3.getAddress(),
            testPerson3.getBirthDate()
        );
        assertFalse(result);
    }

    @Test
    void testUpdatePersonalDetails_testCase4() {
        // Test Case 4: Person attempts to change their birthDate and firstName at the same time
        // Create and add person
        Person testPerson4 = new Person("01-01-2000");
        testPerson4.addPerson();

        // Update person
        Boolean result = testPerson4.updatePersonalDetails(
            testPerson4.getPersonID(), 
            "Updated", 
            testPerson4.getLastName(), 
            testPerson4.getAddress(), 
            "01-01-1999"
        );
        assertFalse(result);
    }

    @Test
    void testUpdatePersonalDetails_testCase5() {
        // Test Case 5: Person attempts to change personID to an invalid ID 
        // Create and add person
        Person testPerson5 = new Person("01-01-2000");
        testPerson5.addPerson();

        // Update person
        Boolean result = testPerson5.updatePersonalDetails(
            "7$&bb!03B1",
            testPerson5.getFirstName(),
            testPerson5.getLastName(), 
            testPerson5.getAddress(), 
            testPerson5.getBirthDate()
        );
        assertFalse(result);
    }
}