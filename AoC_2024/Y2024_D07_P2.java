import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D07_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/7");

        //format input
        String[] lines = input.split("\n");
        long[] testValues = new long[lines.length];
        for (int i = 0; i < testValues.length; i++) {
            testValues[i] = Long.parseLong(lines[i].split(":")[0]);
        }
        long[][] operants = new long[lines.length][];
        for (int i = 0; i < operants.length; i++) {
            String[] operantString = (lines[i].split(":")[1].trim()).split(" ");
            operants[i] = new long[operantString.length];
            for (int j = 0; j < operants[i].length; j++) {
                operants[i][j] = Long.parseLong(operantString[j]);
            }
        }

        long sumCorrectValues = 0;
        for (int i = 0; i < testValues.length; i++) {
            if (canBeCorrect(testValues[i], operants[i])) {
                sumCorrectValues += testValues[i];
            }
        }

        //output
        System.out.println(
            "The sum of possibly correxct test values is: " + sumCorrectValues
        );
    }

    private static boolean canBeCorrect(long desiredValue, long[] operants) {
        if (operants.length == 1) {
            return desiredValue == operants[0];
        } else {
            long[] remainingOperants = new long[operants.length - 1];
            for (int i = 1; i < operants.length; i++) {
                remainingOperants[i - 1] = operants[i];
            }
            return (
                recursiveOperations(
                    desiredValue,
                    remainingOperants,
                    operants[0]
                ) ||
                recursiveOperations(
                    desiredValue,
                    remainingOperants,
                    operants[0]
                )
            );
        }
    }

    private static boolean recursiveOperations(
        long desiredValue,
        long[] operants,
        long currentValue
    ) {
        if (operants.length == 1) {
            return (
                (currentValue + operants[0]) == desiredValue ||
                (currentValue * operants[0]) == desiredValue ||
                (Long.parseLong("" + currentValue + operants[0])) ==
                desiredValue
            );
        } else {
            long[] remainingOperants = new long[operants.length - 1];
            for (int i = 1; i < operants.length; i++) {
                remainingOperants[i - 1] = operants[i];
            }
            return (
                recursiveOperations(
                    desiredValue,
                    remainingOperants,
                    currentValue + operants[0]
                ) ||
                recursiveOperations(
                    desiredValue,
                    remainingOperants,
                    currentValue * operants[0]
                ) ||
                recursiveOperations(
                    desiredValue,
                    remainingOperants,
                    Long.parseLong("" + currentValue + operants[0])
                )
            );
        }
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
