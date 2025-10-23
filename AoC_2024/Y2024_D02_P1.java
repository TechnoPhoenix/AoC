import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D02_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/2");

        //split input into reports
        String[] reports = input.split("\n");

        //split reports into levels and check for the reports safety
        int safeCounter = 0;
        for (int i = 0; i < reports.length; i++) {
            boolean ascending = true;
            boolean descending = true;
            boolean adjacendBound = true;
            String[] stringLevels = reports[i].split(" "); //sepereate into levels
            int[] levels = new int[stringLevels.length];
            for (int j = 0; j < levels.length; j++) {
                levels[j] = Integer.parseInt(stringLevels[j].trim());
            }
            //check levels for ascending
            for (int j = 0; j < (levels.length - 1); j++) {
                if (levels[j] > levels[j + 1]) {
                    ascending = false;
                    break;
                }
            }
            //check levels for descending
            for (int j = 0; j < (levels.length - 1); j++) {
                if (levels[j] < levels[j + 1]) {
                    descending = false;
                    break;
                }
            }
            //check for adjacent levels difference
            for (int j = 0; j < (levels.length - 1); j++) {
                int diff = levels[j] - levels[j + 1];
                if (!((diff <= 3 && diff >= 1) || (diff <= -1 && diff >= -3))) {
                    adjacendBound = false;
                }
            }
            //adjust safe counter
            if ((ascending || descending) && adjacendBound) {
                safeCounter++;
            }
        }
        //print result
        System.out.println("The number of safe reports are: " + safeCounter);
    }

    private static String inputToString(String filename) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
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
