package com.example;

public class Main {
    public static void main(String[] args) {
        // Tests adding a person
        Person person = new Person("John", "Smith", "12|Test St|Kew|Victoria|Australia", "11-01-2000");
        System.out.println(person.addPerson());
        System.out.println(person.getAddress() + " " + person.getPersonID() + " " + person.getFirstName() + " "
                + person.getLastName() + " " + person.getBirthDate());

        // Tests updating their details
        person.updatePersonalDetails(person.getPersonID(), "Jane", "Smith", "12|Test St|Kew|Victoria|Australia",
                "11-01-2000");

        // Tests the file writing for the addDemeritPoints method
        for (int i = 0; i < 10; i++) {
            String date = String.format("%02d-01-2024", i + 1);
            String result = person.addDemeritPoints(date, 3);
            System.out.println("Result: " + result);
        }
    }
}