import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term:");



                String searchTerm = in.nextLine();


                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));

                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }


    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while (!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs

        /*
         To do this, you’ll need to iterate over an ArrayList of jobs. Each job is itself a HashMap. While you can get
         each of the items out of the HashMap using the known keys (employer, location, etc.), think instead about
         creating a nested loop to loop over each HashMap. If a new field is added to the job records, this approach
         will print out the new field without any updates to printJobs.

        This is how it is printing now...
        {position type=Data Scientist / Business Intelligence, name=Data Science, employer=Utilidata, location=Rhode Island, core competency=Statistical Analysis}


         This is what the jobs should print out as...

                *****
                position type: Data Scientist / Business Intelligence
                name: Sr. IT Analyst (Data/BI)
                employer: Bull Moose Industries
                location: Saint Louis
                core competency: Statistical Analysis
                *****

                *****
                position type: Web - Back End
                name: Ruby specialist
                employer: LaunchCode
                location: Saint Louis
                core competency: Javascript
                *****

            The first for loop is iterating over the ArrayList, but had to nest another for loop to iterate over
            the hashmaps inside the ArrayList themselves.

            EX: from our studio of counting characters
            for (Map.Entry<Character, Integer> letter : charactersMap.entrySet()) {
            System.out.println(letter.getKey() + ": " + letter.getValue());


            This is what the No Results should look like:

                Search term:
                Example Search Term with No Results
                No Results
                View jobs by (type 'x' to quit):
                0 - Search
                1 - List

            Maybe make an if else statement, if it is true execute the code I currently have
            Else, write code that will print out NO RESULT message

            Maybe something like if All print what we have
            else if search term contains 'postition type' or 'location' or whatever other ones there are
            we can filter through the ArrayList of HashMaps and only print the ones with those values matching the
            specific search term. If none have a value that matches the search term with the key, then No Results message


//This is happening when I press Search and then location
            Search term:
cancun
Does this have anything to do with it?
*****
position type: Data Scientist / Business Intelligence
name: Junior Data Analyst
employer: Lockerdome
location: Saint Louis
core competency: Statistical Analysis
*****


This is happening when I press search and then all
Search term:
cancun
Whats happening here
*****
position type: Data Scientist / Business Intelligence
name: Junior Data Analyst
employer: Lockerdome
location: Saint Louis
core competency: Statistical Analysis
*****


WHAT DOES THAT MEAN? THAT MEANS THAT I NEED TO ADD SOME CONDITIONALS, BUT WHERE??? IN THE PRINTJOBS, CORRECT. IT IS THE
METHOD 'PRINTJOB' AND RIGHT NOW IT IS SETUP TO ALWAYS DISPLAY ALL THE DATA. I NEED TO WRITE A CONDITION THAT WILL PREVENT
THE POSITIONS TO ALWAYS DISPLAY. SOMETHING LIKE... IF ARRAY IS EMPTY....
         */

    //TODO: Create a loop and iterate over the ArrayList of jobs
    //I probably need to create a getter for allJobs ArrayList so I can retrieve the jobs from it
    //When I choose List and All it prints out all the jobs with this code. But, when I pick different List choices
    //it doesn't print. I am assuming I will need to write some conditionals eventually


    private static void printJobs(ArrayList<HashMap<String, String>> jobs) {

        if (jobs.isEmpty()) {
            System.out.print("No Results");
        } else if (!jobs.isEmpty()) {
            for (HashMap<String, String> job : jobs) {
                System.out.println();
                System.out.println("*****");
                for (Map.Entry<String, String> item : job.entrySet()) {
                    System.out.println(item.getKey() + ": " + item.getValue());
                }
                System.out.println("*****");
            }

        } else {

            for (HashMap<String, String> job : JobData.getAllJobs()) {
                System.out.println("*****");
                for (Map.Entry<String, String> item : job.entrySet()) {
                    System.out.println(item.getKey() + ": " + item.getValue());
                }
                System.out.println("*****");
                System.out.println();
            }
        }
    }
}