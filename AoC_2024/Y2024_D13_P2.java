import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Y2024_D13_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/13");

        //split input into different machines
        String[] machines = input.split("\n\n");

        //solve porblem for each claw machine
        long totalTokens = 0;
        for (String machine : machines) {
            //extract information from this machines string
            String[] lines = machine.split("\n");

            String[] buttonA = (lines[0].substring(
                    10,
                    lines[0].length()
                )).split(", ");
            String[] buttonB = (lines[1].substring(
                    10,
                    lines[1].length()
                )).split(", ");
            String[] prize = (lines[2].substring(7, lines[2].length())).split(
                ", "
            );

            Pattern pattern = Pattern.compile("(X|Y)(\\+|=)([0-9]*)");
            Matcher matcher;

            int xDiffA;
            int yDiffA;
            int xDiffB;
            int yDiffB;
            long xPrize;
            long yPrize;

            try {
                matcher = pattern.matcher(buttonA[0].trim());
                matcher.matches();
                xDiffA = Integer.parseInt(matcher.group(3));

                matcher = pattern.matcher(buttonA[1].trim());
                matcher.matches();
                yDiffA = Integer.parseInt(matcher.group(3));

                matcher = pattern.matcher(buttonB[0].trim());
                matcher.matches();
                xDiffB = Integer.parseInt(matcher.group(3));

                matcher = pattern.matcher(buttonB[1].trim());
                matcher.matches();
                yDiffB = Integer.parseInt(matcher.group(3));

                matcher = pattern.matcher(prize[0].trim());
                matcher.matches();
                xPrize = 10_000_000_000_000l + Long.parseLong(matcher.group(3));

                matcher = pattern.matcher(prize[1].trim());
                matcher.matches();
                yPrize = 10_000_000_000_000l + Long.parseLong(matcher.group(3));

                //compute minimum tokens required
                totalTokens += calcReqTokens(
                    xDiffA,
                    yDiffA,
                    xDiffB,
                    yDiffB,
                    xPrize,
                    yPrize
                );
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        System.out.println(
            "The total token cost for all winnable prizes is: " + totalTokens
        );
    }

    private static long calcReqTokens(
        int xDiffA,
        int yDiffA,
        int xDiffB,
        int yDiffB,
        long xPrize,
        long yPrize
    ) {
        long bPresses =
            ((yDiffA * xPrize) - (xDiffA * yPrize)) /
            (yDiffA * xDiffB - xDiffA * yDiffB);
        long aPresses = (xPrize - (bPresses * xDiffB)) / xDiffA;

        if (
            ((xDiffA * aPresses) + (xDiffB * bPresses)) == xPrize &&
            ((yDiffA * aPresses) + (yDiffB * bPresses)) == yPrize
        ) {
            return ((aPresses * 3) + (bPresses * 1));
        }

        return 0;
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
