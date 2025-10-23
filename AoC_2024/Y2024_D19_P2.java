import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Y2024_D19_P2 {

    static HashMap<Character, ArrayList<String>> possibleTowels;
    static HashMap<String, Long> cache;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/19");

        //split input into available towels and desired patterns
        String[] splitInput = input.split("\n\n");
        String[] availableTowels = splitInput[0].split(",");
        cache = new HashMap<String, Long>();
        possibleTowels = new HashMap<Character, ArrayList<String>>();
        possibleTowels.put('w', new ArrayList<String>());
        possibleTowels.put('u', new ArrayList<String>());
        possibleTowels.put('b', new ArrayList<String>());
        possibleTowels.put('r', new ArrayList<String>());
        possibleTowels.put('g', new ArrayList<String>());
        for (int i = 0; i < availableTowels.length; i++) {
            availableTowels[i] = availableTowels[i].trim();
            possibleTowels
                .get(availableTowels[i].charAt(0))
                .add(availableTowels[i]);
        }
        String[] desiredPatterns = splitInput[1].split("\n");

        //iterate over desired patterns and attempt to arrange towels accordingly
        long possibleArrangements = 0;
        for (int i = 0; i < desiredPatterns.length; i++) {
            possibleArrangements += calcPossibleArrangements(
                desiredPatterns[i]
            );
        }

        //output
        System.out.println(
            "The number of possible arrangements is: " + possibleArrangements
        );
    }

    private static long calcPossibleArrangements(String desiredPattern) {
        if (desiredPattern.isEmpty()) {
            return 1;
        }
        if (cache.containsKey(desiredPattern)) {
            return cache.get(desiredPattern);
        }

        long possibleArrangements = 0;
        ArrayList<String> list = possibleTowels.get(desiredPattern.charAt(0));
        for (String towel : list) {
            if (towel.length() > desiredPattern.length()) {
                continue;
            }
            if (desiredPattern.startsWith(towel)) {
                String remainingPattern = desiredPattern.substring(
                    towel.length()
                );
                possibleArrangements += calcPossibleArrangements(
                    remainingPattern
                );
            }
        }
        cache.put(desiredPattern, possibleArrangements);
        return possibleArrangements;
    }

    private static String inputToString(String filename) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }
}
