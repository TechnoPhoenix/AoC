import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D01_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/1");

        //turn input string into format to solve problem
        int[] left = new int[1000];
        int[] right = new int[1000];
        String[] lines = input.split("\n"); //split into lines
        for (int i = 0; i < lines.length; i++) {
            int middle = lines[i].indexOf("   "); //split each line into left and right number
            left[i] = Integer.parseInt(lines[i].substring(0, middle));
            right[i] = Integer.parseInt(
                lines[i].substring(middle + 3, lines[i].length() - 1)
            );
        }

        //count occurences of left list numbers in right list
        int[] occurences = new int[1000];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines.length; j++) {
                if (left[i] == right[j]) {
                    occurences[i]++;
                }
            }
        }

        //calculate individual and total score
        int totalScore = 0;
        int[] scores = new int[1000];
        for (int i = 0; i < occurences.length; i++) {
            scores[i] = left[i] * occurences[i];
            totalScore += scores[i];
        }

        //output result
        System.out.println(
            "The similarity score of the two lists is: " + totalScore
        );
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
