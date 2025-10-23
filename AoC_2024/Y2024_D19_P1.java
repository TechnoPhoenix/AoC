import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Y2024_D19_P1 {

    static HashMap<Character, ArrayList<String>> possibleTowels;
    static HashMap<String, Boolean> cache;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/19");

        //split input into available towels and desired patterns
        String[] splitInput = input.split("\n\n");
        String[] availableTowels = splitInput[0].split(",");
        cache = new HashMap<String, Boolean>();
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
        int possiblePatterns = 0;
        for (int i = 0; i < desiredPatterns.length; i++) {
            if (patternPossible(desiredPatterns[i])) {
                possiblePatterns++;
            }
        }

        //output
        System.out.println(
            "The number of desings that are possible is: " + possiblePatterns
        );
    }

    private static boolean patternPossible(String desiredPattern) {
        if (cache.containsKey(desiredPattern)) {
            return cache.get(desiredPattern);
        }
        ArrayList<String> list = possibleTowels.get(desiredPattern.charAt(0));
        //iterate over available towels and attempt to match to beginning of desired pattern
        for (String towel : list) {
            if (towel.length() > desiredPattern.length()) {
                continue;
            }
            if (desiredPattern.startsWith(towel)) {
                String remainingPattern = desiredPattern.substring(
                    towel.length()
                );
                if (!remainingPattern.equals("")) {
                    if (patternPossible(remainingPattern)) {
                        cache.put(remainingPattern, true);
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        cache.put(desiredPattern, false);
        return false;
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
