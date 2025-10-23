import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D02_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/2");

        //split input into reports
        String[] reports = input.split("\n");

        //split reports into levels and check the reports safety
        int safeCounter = 0;
        for (int i = 0; i < reports.length; i++) {
            String[] stringLevels = reports[i].split(" "); //sepereate into levels
            int[] levels = new int[stringLevels.length];
            for (int j = 0; j < levels.length; j++) {
                levels[j] = Integer.parseInt(stringLevels[j].trim());
            }
            if (checkSafetyAllNotations(levels)) {
                safeCounter++;
            }
        }

        //print result
        System.out.println(
            "The total number of safe reports with the problem dampener is: " +
                safeCounter
        );
    }

    private static boolean checkSafetyAllNotations(int[] levels) {
        //check complete report
        if (checkSafety(levels)) {
            return true;
        }

        //notations with one missing level
        for (int i = 0; i < levels.length; i++) {
            //generate notation
            int[] temp = new int[levels.length - 1];
            boolean extra = false;
            for (int j = 0; j < levels.length; j++) {
                if (i == j) {
                    extra = true;
                    continue;
                }

                if (extra) {
                    temp[j - 1] = levels[j];
                } else {
                    temp[j] = levels[j];
                }
            }

            //check notation
            if (checkSafety(temp)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSafety(int[] levels) {
        boolean ascending = true;
        boolean descending = true;
        boolean adjacendBound = true;
        //check levels for ascending
        for (int i = 0; i < (levels.length - 1); i++) {
            if (levels[i] > levels[i + 1]) {
                ascending = false;
                break;
            }
        }
        //check levels for descending
        for (int i = 0; i < (levels.length - 1); i++) {
            if (levels[i] < levels[i + 1]) {
                descending = false;
                break;
            }
        }
        //check for adjacent levels difference
        for (int i = 0; i < (levels.length - 1); i++) {
            int diff = levels[i] - levels[i + 1];
            if (!((diff <= 3 && diff >= 1) || (diff <= -1 && diff >= -3))) {
                adjacendBound = false;
            }
        }
        //adjust safe counter
        if ((ascending || descending) && adjacendBound) {
            return true;
        }
        return false;
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
