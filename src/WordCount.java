
// Fig. 16.17: WordTypeCount.java
// Program counts the number of occurrences of each word in a String.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordCount {
    public static void main(String[] args) {
        double threshold =  Double.valueOf(args[2]);
        String filename = args[0];
        String filename2 = args[1];

        try
        {
            Map fileMap1 = createMap(filename); // create map based on user input
            Map fileMap2 = createMap(filename2); // create map based on user input

            double score = CompareMaps(fileMap1,fileMap2);//JaccardIndex

            String stringScore = String.format("%.6g", score/100.0); //format JaccardIndex
            String stringThreshold = String.format("%.6g", threshold); //format threshold

            if (((threshold * 100.0) > score)) {
                System.out.println("It is not a plagairism case. Simiilarity score = "+ stringScore  + " < threshold score " +stringThreshold );
            }
            if (((threshold * 100.0) < score)) {
                System.out.println("It is  a plagairism case. Simiilarity score = "+ stringScore + " > threshold score " + stringThreshold);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }



    }




    //Method to get Jaccard Index
    private static double CompareMaps(Map fileMap1,Map fileMap2){


        //Creat set of all keys in maps
        TreeSet <String> set1 = new TreeSet<>(fileMap1.keySet());
        TreeSet <String> set2 = new TreeSet<>(fileMap2.keySet());



        //Get intersection set
        Set<String> intersection = new TreeSet<String>(set1); // use the copy constructor
        intersection.retainAll(set2);

        //Get intersection set
        Set<String> union = new TreeSet<String>(set1); // use the copy constructor
        union.addAll(set2);

        //Calculate total size and intersection size
        int totalSize = union.size();
        int intersectionSize = intersection.size();


        //Calculate jaccard Index
        double jaccardIndex =  ((double) (intersectionSize) / totalSize) * 100.0 ;

        System.out.println("Number of unique words in document 1: " + fileMap1.size());
        System.out.println("Number of unique words in document 2: " + fileMap2.size());
        System.out.println("They have " + intersection.size() + " words in common");
        System.out.println("The common words and their counts in document 1 and document 2, respectively");

        //Display keys and their counts in each file
        displayMap(fileMap1,fileMap2,intersection);

        return jaccardIndex;


    }


    // create map from user input
    private static Map<String, Integer> createMap(String filename) throws  FileNotFoundException {
        List<String> data = new ArrayList<String>();
        File inFile = new File(filename);
        Scanner in = new Scanner(inFile);

        //handle punctuation
        in.useDelimiter("\\s+|(\\.|,|-|:|!|\\?|\"|\\[|\\]|\\(|\\)|'$|\\r|\\n)*(\\s)*(\\.|,|-|:|!|\\?|\"|\\[|\\]|\\(|\\)|'$|\\r|\\n)+(\\s)*");

        //add every word to array
        while (in.hasNext()){
            data.add(in.next().toLowerCase());

        }
        //Change array to string
        String textString = data.toString().replace("]","").replace("[","").replace(",","");





        //Initialize map
        Map<String, Integer> map = new HashMap<>();

        // tokenize the input
        String[] tokens = textString.split(" ");

        // processing input text
        for (String word : tokens) {

            // if the map contains the word
            if (map.containsKey(word)) { // is word in map?
                int count = map.get(word); // get current count
                map.put(word, count + 1); // increment count
            }
            else {
                map.put(word, 1); // add new word with a count of 1 to map
            }
        }
        return map;
    }

    // display map content
    private static void displayMap(Map map1, Map map2,Set<String> intersectionSet) {

        System.out.printf("Key\t\tCount1\tCount2%n");

        // generate output for each key in map
        for (String key : intersectionSet) {
            System.out.printf("%-10s%10s%10s%n", key, map1.get(key),map2.get(key));

        }


    }
}

