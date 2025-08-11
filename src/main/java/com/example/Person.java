package com.example;

import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offence day
    private boolean isSuspended;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private static String ALL_VALID_CHARS = "!?.@#$%^&*()-_+=:;~1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String SPECIAL_CHARS = "!?.@#$%^&*()-_+=:;~";
    private static String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //*************************
    // CONSTRUCTORS
    public Person() {
        this.personID = "";
        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.birthDate = "";
        this.demeritPoints = new HashMap<Date, Integer>();
        this.isSuspended = false;

        sdf.setLenient(false);
    }

    public Person(String firstName, String lastName, String address, String birthDate) {
        // Constructor which gives Person a randomly generated ID
        this(); // Call default person constructor

        // Set parameters
        this.personID = generatePersonID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Person(String personID, String firstName, String lastName, String address, String birthDate) {
        // Constructor which lets you define personID
        this();

        // Set parameters
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Person(String birthDate) {
        // Constructor for testing birth date. All other parameters are valid
        this();

        // Set parameters
        this.personID = "99__aaaaAA"; // Example ID for testing;
        this.firstName = "Test";
        this.lastName = "User";
        this.address = "1|Test St|Kew|Victoria|Australia";
        this.birthDate = birthDate;
    }

    //*************************
    // GETTERS
    public String getPersonID() {return this.personID;} 
    public String getFirstName() {return this.firstName;}
    public String getLastName() {return this.lastName;}
    public String getAddress() {return this.address;}
    public String getBirthDate() {return this.birthDate;}
    public HashMap<Date, Integer> getDemeritPoints() {return this.demeritPoints;}
    public boolean getSuspended() {return this.isSuspended;}


    //*************************
    // OTHER METHODS

    /**
     * Generates a random person ID. Not necessarily valid
     * 
     * @return personID
     */
    private static String generatePersonID() {
        
        String personID = "";
        int d = (int) new Date().getTime();
        Random random = new Random(d);

        for(int i = 0; i < 2; i++){
            personID += random.nextInt(9-2 +1) + 2;
        }
        for(int i = 0; i < 6; i++){
            personID += Person.ALL_VALID_CHARS.charAt(random.nextInt(Person.ALL_VALID_CHARS.length()));
        }
        for (int i = 0; i < 2; i++) {
            personID += Person.UPPERCASE_CHARS.charAt(random.nextInt(Person.UPPERCASE_CHARS.length()));
        }
        while (!Person.validatePersonID(personID)){
            personID = generatePersonID();
        }
        return personID;
    }

    /**
     * Checks if a personID is valid. Valid example: "56s_@d%fAB"
     * 
     * Conditions:
     * 1. PersonID should be exactly 10 characters long
     * 2. The first two characters should be numbers between 2 and 9
     * 3. There should be at least two special characters between characters 3 and 8
     * 4. The last two characters should be upper case letters (A - Z). 
     * 
     * @param personID - ID to check the validity of
     * @return Boolean of whether the ID is valid
     */
    public static boolean validatePersonID(String personID) {
        // PersonID should be exactly 10 characters long
        if (personID.length() != 10) {
            // Not 10 characters long
            return false;
        }

        // The first two characters should be numbers between 2 and 9
        for (int i = 0; i < 2; i++) {
            char c = personID.charAt(i);
            if (c < '2' || c > '9') {
                // Not a number between 2 and 9
                return false; 
            }
        }

        // There should be at least two special characters between characters 3 and 8
        int countSpecial = 0;
        for (int i=2; i<8; i++) {
            char c = personID.charAt(i);
            if (Person.SPECIAL_CHARS.indexOf(c) != -1) {
                countSpecial += 1;
            }
        }
        if (countSpecial < 2) {
            // Less than two special characters
            return false;
        }

        // The last two characters should be upper case letters (A - Z)
        for (int i=0; i<2; i++) {
            char c = personID.charAt(personID.length() - 1 - i);
            if (!Character.isUpperCase(c)) {
                // Not an upper case character
                return false;
            }
        }

        // If all checks have passed, return true
        return true;
    }

    /**
     * Checks if an address is valid. Example: "32|Highland Street|Melbourne|Victoria|Australia"
     * 
     * Conditions:
     * 1. Address must follow the format: "Street Number|Street|City|State|Country"
     * 2. State is always "Victoria"
     * 
     * @param address
     * @return true if the address is valid
     */
    public static boolean validateAddress(String address) {
        String[] addressStrings = address.split("\\|");
        
        // Check if address has the correct number of fields
        if(addressStrings.length != 5) {
            return false;
        }

        // Check if state is Victoria
        if(!addressStrings[3].equals("Victoria")) {
            return false;
        }

        // Check if country is Australia
        if(!addressStrings[4].equals("Australia")) {
            return false;
        }

        // Otherwise return true
        return true;
    }

    /**
     * Checks if a dateString is valid, following format DD-MM-YYYY
     * 
     * @param dateString the dateString to check
     * @return true if the date is valid
     */
    public static boolean validateDateString(String dateString) {
        // Try to parse dateString
        try {
            sdf.parse(dateString);
        } catch (ParseException e) {
            // Invalid date
            return false;
        }

        // Valid date
        return true;
    }

    /**
     * Calculates how many years there are between two different dates.
     * Used for age calculations, as well as how old someone was when they got demerit points.
     * 
     * @param date1
     * @param date2
     * @return Number of years between the dates. Always a positive number
     */
    private static int yearsBetweenDates(Date date1, Date date2) {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        if (date1.after(date2)) {
            calStart.setTime(date2);
            calEnd.setTime(date1);
        } else {
            calStart.setTime(date1);
            calEnd.setTime(date2);
        }

        int yearsBetween = calEnd.get(Calendar.YEAR) - calStart.get(Calendar.YEAR);
        if (calEnd.get(Calendar.DAY_OF_YEAR) < calStart.get(Calendar.DAY_OF_YEAR)) {
            yearsBetween --;
        }
        return yearsBetween;
    }
    /**
     * @param date1String
     * @param date2
     * @return Number of years between the dates. Always a positive number.
     * @throws RuntimeException if an incorrect dateString is passed
     */
    private static int yearsBetweenDates(String date1String, Date date2) {
        Date date1;
        try {
            date1 = sdf.parse(date1String);
        } catch (ParseException e) {
            throw new RuntimeException("Incorrect String date1String", e);
        }
        return yearsBetweenDates(date1, date2);
    }
    
    /**
     * Writes the data from a Person instance to a file
     * Uses CSV formatting
     * 
     * @param person Person data to write to file
     * @param fileName Filename to update
     * @param lineNumber -1 for appending to file, otherwise overwrites a specific line
     * @return true if successful
     * @throws RuntimeException if an I/O error occurs during file writing
     */
    private static boolean filePersonWrite(String fileName, Person person, int lineNumber) {
        try {
            // Read data from file
            List<String> fileData = new ArrayList<>();
            File file = new File(fileName);
            if (file.exists()) {
                fileData = Files.readAllLines(file.toPath());
            } else {
                fileData.add("personID,firstName,lastName,address,birthDate");
            }

            // Create person string
            String personString = person.getPersonID() + ","
                                + person.getFirstName() + ","
                                + person.getLastName() + ","
                                + person.getAddress() + ","
                                + person.getBirthDate();

            // Append personString to file data
            if (lineNumber == -1) {
                fileData.add(personString);
            } else if (lineNumber >= 0 && lineNumber < fileData.size()) {
                fileData.set(lineNumber, personString);
            }

            // Write fileData back to file
            Files.write(file.toPath(), fileData);
            return true;
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    /**
     * Finds a person in a file by searching for personID
     * 
     * @param fileName Name of the file to check
     * @param personID Person ID to search for
     * @return index of person, otherwise -1 if not found
     * @throws RuntimeException if an I/O error occurs during file writing
     */
    private static int filePersonFind(String fileName, String personID) {
        try {
            // Read data from file
            List<String> fileData = new ArrayList<>();
            File file = new File(fileName);
            if (file.exists()) {
                fileData = Files.readAllLines(file.toPath());
            } else {
                // Return -1 if file doesn't exist
                return -1;
            }

            // Find person in fileData
            for (int i = 0; i < fileData.size(); i++) {
                String fileDataPerson = fileData.get(i);
                String fileDataID = fileDataPerson.split(",")[0];

                // If IDs match, return index
                if (fileDataID.equals(personID)) {
                    return i;
                }
            }

            // Not found, return -1
            return -1;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    /**
     * Adds demerit point records to a file
     * 
     * @param fileName
     * @param personID
     * @param offenceDateString
     * @param points
     * @param totalPoints
     * @return true if successful
     */
    private static boolean fileDemeritAdd(String fileName, String personID, String offenceDateString, int points, int totalPoints, boolean isSuspended) {
        try {
            // Read from file
            List<String> fileData = new ArrayList<>();
            File file = new File(fileName);
            if (file.exists()) {
                fileData = Files.readAllLines(file.toPath());
            } else {
                fileData.add("personID,offenceDate,demeritPoints,totalDemeritPoints,isSuspended");
            }

            // Create demerit string
            String demeritString = personID + ","
                                 + offenceDateString + ","
                                 + points + ","
                                 + totalPoints + ","
                                 + isSuspended;

            // Append demeritString to file data
            fileData.add(demeritString);

            // Write demerit points to file
            Files.write(file.toPath(), fileData);
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    /**
     * Adds a person's information to a TXT file if all conditions are met.
     * 
     * Conditions:
     * 1. PersonID must be exactly 10 characters long, with the first two characters as numbers (2-9),
     *    at least two special characters between characters 3 and 8, and the last two characters as uppercase letters (A-Z).
     * 2. Address must follow the format: "Street Number|Street|City|State|Country", where State is "Victoria".
     * 3. BirthDate must be in the format: "DD-MM-YYYY".
     * 4. FileName must be a valid file name ("fileName.txt").
     * 
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param address the address of the person in a specified format
     * @param birthDate the birthDate of the person in "DD-MM-YYYY" format
     * @return true if the person's information is successfully added to the file; false otherwise
     */
    public boolean addPerson() {
        // If Person's information meets all the required conditions, insert the information into a TXT file, and return true
        // Otherwise, don't insert it into the TXT file, and return false

        // Condition 1: PersonID should be valid
        if (!Person.validatePersonID(this.personID)) {
            return false;
        }

        // Condition 2: The address should follow the correct format
        if (!Person.validateAddress(this.address)) {
            return false;
        }
        
        // Condition 3: The format of the birth date of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        if(!Person.validateDateString(birthDate)) {
            return false;
        }

        // Add person to file (overwrite if they already exist)
        int index = filePersonFind("Person.txt", this.personID);
        return filePersonWrite("Person.txt", this, index);
    }

    /**
     * Update a person's ID, firstName, lastName, address and birthDate in a TXT file
     * Changing personal details will not affect their demerit points or the suspension status.
     * All relevant conditions for the addPerson function are also checked here.
     * 
     * @param personID
     * @param firstName
     * @param lastName
     * @param address
     * @param birthDateString
     * @return
     */
    public boolean updatePersonalDetails(String personID, String firstName, String lastName, String address, String birthDate) {
        // Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
        // the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true;
        // Otherwise, the Person's updated information should not be updated in the TXT file, and the updatePersonalDetails function should return false.

        // Get current/old details
        String oldPersonID = this.personID;
        String oldFirstName = this.firstName;
        String oldLastName = this.lastName;
        String oldAddress = this.address;
        String oldBirthDate = this.birthDate;

        // Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e., person's ID, firstName, lastName, address) can be changed
        if (!birthDate.equals(oldBirthDate)) {
            // Make sure no other details are changed
            if (!personID.equals(oldPersonID)) {
                return false;
            }
            if (!firstName.equals(oldFirstName)) {
                return false;
            }
            if (!lastName.equals(oldLastName)) {
                return false;
            }
            if (!address.equals(oldAddress)) {
                return false;
            }
            if (!birthDate.equals(oldBirthDate)) {
                return false;
            }
        }

        // Condition 1: If a person is under 18, their address cannot be changed.
        if (!validateDateString(birthDate)) {
            return false;
        }
        int age = yearsBetweenDates(birthDate, Calendar.getInstance().getTime());
        if (age < 18) {
            // If person is under 18, and their address is changing, return false
            if (!address.equals(oldAddress)) {
                return false;
            }
        }

        // Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed
        char c = oldPersonID.charAt(0);
        if ((int)c % 2 == 0) {
            if (!personID.equals(oldPersonID)) {
                return false;
            }
        }

        // Check addPerson conditions
        // Condition 1: PersonID should be valid
        if (!Person.validatePersonID(personID)) {
            return false;
        }

        // Condition 2: The address should follow the correct format
        if (!Person.validateAddress(address)) {
            return false;
        }
        
        // Condition 3: The format of the birth date of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        if(!Person.validateDateString(birthDate)) {
            return false;
        }

        // If ID is being changed, make sure new ID doesn't already exist in database (prevent duplicates)
        if (!this.personID.equals(personID) && filePersonFind("Person.txt", personID) != -1) {
            return false;
        }

        // Make sure person to update is in database
        int index = filePersonFind("Person.txt", this.personID);
        if (index == -1) {
            // Person not found in database, can't update
            return false;
        }

        // If all conditions pass, update person details
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;

        // Update/write over person in database
        return filePersonWrite("Person.txt", this, index);
    }

    /**
     * Adds demerit points for a given person in a TXT file
     * 
     * Conditions:
     * 1. The format of the date of the offence should follow the following format: DD-MM-YYYY. Example: 15-11-1990
     * 2. The demerit points must be a whole number between 1–6
     * 3. If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
     *    If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
     * 
     * @param offenceDateStr
     * @param points
     * @return "Success" if successful, otherwise "Failed"
     */
    public String addDemeritPoints(String offenceDateStr, Integer points) {
        // Instruction: If all conditions are met, insert demerit points into the TXT file and return "Success"
        //              Otherwise, don't add them, and return "Failed"
         // Ensure demeritPoints is defined
        // Condition 1: The offenceDate is in the correct format
        
        Date offenceDate;
        Date birthDate;
        if (!validateDateString(offenceDateStr)) { return "Failed";}
        if (!validateDateString(this.birthDate)) { return "Failed"; }
        try {
            offenceDate = sdf.parse(offenceDateStr);
            birthDate = sdf.parse(this.birthDate); 
        }
        catch (ParseException e) { return "Failed"; }

        // Condition 2: The demerit points must be a whole number between 1–6
        if (points < 1 || points > 6) { return "Failed"; }
        this.demeritPoints.put(offenceDate, points);
        
        // Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        // If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
        int ageAtOffence = yearsBetweenDates(birthDate, offenceDate);
        int totalPoints = 0;
        Calendar twoYearsAgo = Calendar.getInstance();
        twoYearsAgo.add(Calendar.YEAR, -2);
        for (Map.Entry<Date, Integer> entry : this.demeritPoints.entrySet()) {
            if (entry.getKey().after(twoYearsAgo.getTime())) {
                totalPoints += entry.getValue();
            }
        }

        if (ageAtOffence < 21 && totalPoints > 6) {
            this.isSuspended = true;
        } else if (ageAtOffence >= 21 && totalPoints > 12) {
            this.isSuspended = true;
        }

        // Add demerit points to the file and return "Success"
        if (fileDemeritAdd("DemeritPoints_" + this.personID + ".txt", this.personID, offenceDateStr, points, totalPoints, isSuspended)) {
            return "Success";
        } else {
            return "Failed";
        }
    }
}
