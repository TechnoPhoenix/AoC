import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D22_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/22");

        //split into different buyers inital numbers
        String[] initialString = input.split("\n");
        int[] initialNumbers = new int[initialString.length];
        for (int i = 0; i < initialNumbers.length; i++) {
            initialNumbers[i] = Integer.parseInt(initialString[i].trim());
        }

        long[] resultingNumbers = new long[initialNumbers.length];
        for (int i = 0; i < initialNumbers.length; i++) {
            long number = initialNumbers[i];
            for (int j = 0; j < 2000; j++) {
                number = (number ^ (number * 64)) % 16777216;
                number = (number ^ (number / 32)) % 16777216;
                number = (number ^ (number * 2048)) % 16777216;
            }
            resultingNumbers[i] = number;
        }

        long sumOfNumbers = 0;
        for (int i = 0; i < resultingNumbers.length; i++) {
            sumOfNumbers += resultingNumbers[i];
        }

        //output
        System.out.println(
            "The sum of all buyers 200th secret numbers is: " + sumOfNumbers
        );
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
