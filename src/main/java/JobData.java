import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    //TODO: Creating a getter method for allJobs ArrayList
    //"non-static method .getAllJobs() can not be referenced from a static context."
    //So, I made this a public static ArrayList...
    public static ArrayList<HashMap<String, String>> getAllJobs(){
        return allJobs;
    }


    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }



    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        // TODO - implement this method
        //We will have an arrayList filled with hashMaps (key/value pairs) We will call it 'searchJobs'
        //We need to loop over allJobs to look for the search term (value).
        //So we need to loop over every key and look inside the values of each of the jobs.

        //Case sensitivity, if we were doing equals I could use equalsIgnoreCase, but then it would rule out if the
        //search term was mentioned in the value. So, I think I would have to just make the value and search term set
        //to either Upper or Lowercase. I would think this needs to go here for All search & also for searching the
        //columns as well.

        ArrayList<HashMap<String, String>> searchJobs = new ArrayList<>();

        for (HashMap<String, String> job : allJobs) {

            for (String searchKey : job.keySet()) {
                if (job.get(searchKey).toLowerCase().contains(value.toLowerCase())) {
                    if (!searchJobs.contains(job)) {
                        searchJobs.add(job);
                    }
                }
            }
        }
        return searchJobs;
    }


    /*
    This is from the studio counting characters: We looped over each letter

            char[] charactersInString = quote.toUpperCase().toCharArray();

        HashMap<Character, Integer> charactersMap = new HashMap<>();

        for (char letter : charactersInString) {


            if (charactersMap.containsKey(letter)) {
                charactersMap.put(letter, charactersMap.get(letter) + 1);
            } else {
                charactersMap.put(letter, 1);
            }

        }
     */

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}