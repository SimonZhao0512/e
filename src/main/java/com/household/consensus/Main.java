package com.household.consensus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/* thought:
 * read the file line by line-> manipulate the line -> convert each line into a Person then place into the list personList
 * create a hashmap <address, how_many_occupants>
 * find the occupants that live in each household and sort them and print them
 * 
 * 
 * read the file, use for each loop to get each line into a new Person instance, then put each instance in the list<Person>
 * make a Map for each household, 
 */

public class Main {

    static ArrayList<Person> personList = new ArrayList<Person>();
    static HashMap<String, Integer> householdOccupantsMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        readFile("asset/input_data.txt");
        householdConsensus();
        printoutResult();

    }

    // sort occupants in each household and print out the fina result
    static void printoutResult() {
        System.out.println("Household Consensus: only showing detailed info for adults (>18)");
        System.out.println("");
        for (String key : householdOccupantsMap.keySet()) {
            ArrayList<Person> eachHouseholdOccupantsList = new ArrayList<Person>();
            System.out.println("Household " + key + ", Number of Occupants: " + householdOccupantsMap.get(key));
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).address.equalsIgnoreCase(key)) {
                    eachHouseholdOccupantsList.add(personList.get(i));
                }
            }
            Comparator<Person> personComparator = Comparator
                    .comparing(Person::getLName)
                    .thenComparing(Person::getFName)
                    .thenComparing(Person::getAge);
            Collections.sort(eachHouseholdOccupantsList, personComparator);
            for (Person person : eachHouseholdOccupantsList) {
                if (person.getAge() > 18) {
                    System.out.println(person.getFName() + " " + person.getLName() +
                            " - Address: " + person.getAddress() + ", Age: " + person.getAge());
                }
            }
            System.out.println("-----------------------------  \n");

        }
    }

    // populate the householdOccupantsMap <address, how_many_occupants_in_it>
    static void householdConsensus() {
        for (int i = 0; i < personList.size(); i++) {
            String address = personList.get(i).address;

            if (!householdOccupantsMap.containsKey(address)) {
                householdOccupantsMap.put(address, 1);
            } else {
                householdOccupantsMap.put(address, householdOccupantsMap.get(address) + 1);
            }

        }

    }

    // read the txt file and place each line as a Person into the personList<Person>
    static void readFile(String filepath) {
        try (FileInputStream fis = new FileInputStream(filepath);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] components = line.split("\",");
                for (int i = 0; i < components.length; i++) {
                    components[i] = components[i].replace("\"", "").replace(".", "").replace(",", "");
                }
                // System.out.println(Arrays.toString(components));
                Person person = new Person(components[0], components[1],
                        (components[2].trim() + ", " + components[3].trim() + ", " + components[4].trim())
                                .toUpperCase(),
                        Byte.parseByte(components[5].replace("\"", "")));
                personList.add(person);

            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
