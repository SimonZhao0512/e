package com.household.consensus;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class MainTest {
    /**
     * Rigorous Test :-)
     */

    @Test
    @DisplayName("test readFile method with non-empty file and invalid data")
    public void testReadFileInvalidData() {
        assumeTrue(isInputFileNotEmpty(), "The input_data.txt file is empty");
        Main.personList.clear();

        Main.readFile("asset/input_data.txt");
        assertNotNull(Main.personList, "Person list is null, but shouldn't be, check logic");
        assertTrue(Main.personList.size() >= 1);
        Main.personList.clear();

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> Main.readFile("asset/invalid_data_test.txt"));
        assertNotNull(Main.personList, "Person list is null, but shouldn't be, check logic");
        assertTrue(Main.personList.size() == 0);

    }

    @Test
    @DisplayName("Test readFile method with empty file")
    public void testReadFileEmptyFile() {
        Main.personList.clear();
        Main.readFile("asset/empty_file_test.txt");
        assertTrue(Main.personList.isEmpty(), "Person list should be empty");
    }

    @Test
    @DisplayName("Test householdConsensus method")
    public void testHouseholdConsensus() {
        Main.personList.clear();
        Main.personList.add(new Person("John", "Doe", "123 Main St, Seattle, WA", (byte) 25));
        Main.personList.add(new Person("Jane", "Doe", "123 Main St, Seattle, WA", (byte) 30));
        Main.personList.add(new Person("Bob", "Smith", "456 Oak St, Tacoma, WA", (byte) 40));

        Main.householdConsensus();

        assertEquals(2, Main.householdOccupantsMap.size(), "There should be 2 households");

        assertTrue(Main.householdOccupantsMap.containsKey("123 Main St, Seattle, WA"),
                "Household map should contain the first address");
        assertEquals(2, Main.householdOccupantsMap.get("123 Main St, Seattle, WA"),
                "Invalid occupants count for the first household");

        assertTrue(Main.householdOccupantsMap.containsKey("456 Oak St, Tacoma, WA"),
                "Household map should contain the second address");
        assertEquals(1, Main.householdOccupantsMap.get("456 Oak St, Tacoma, WA"),
                "Invalid occupants count for the second household");

    }

    private boolean isInputFileNotEmpty() {
        // Assuming your file is in the src folder
        String filePath = "asset/input_data.txt";

        // Use appropriate logic to check if the file is not empty
        // For example, you can use java.nio.file.Files and java.nio.file.Path

        // Example:
        try {
            return Files.size(Paths.get(filePath)) > 0;
        } catch (IOException e) {
            // Handle exception or return false
            return false;
        }
    }

}
