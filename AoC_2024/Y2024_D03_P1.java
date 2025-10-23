import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D03_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/3");

        //split string to detect mul instructions
        String[] possibleMulInstructions = input.split("mul");
        for (int i = 0; i < possibleMulInstructions.length; i++) {
            possibleMulInstructions[i] = "mul" + possibleMulInstructions[i];
        }

        //regex black magic to detect valid instructions and execute them
        int sumOfMultiplications = 0;
        for (int i = 0; i < possibleMulInstructions.length; i++) {
            //detect strings starting with a valid multiplication instruction
            if (
                !possibleMulInstructions[i].matches(
                    "mul\\([0-9]{1,3},[0-9]{1,3}\\).*"
                )
            ) {
                continue;
            }
            String instruction =
                possibleMulInstructions[i].substring(
                    0,
                    (possibleMulInstructions[i].indexOf(")"))
                ) +
                ")"; //trim string to instruction

            //extract operants from instruction
            String operantSubstring = (instruction.substring(
                    instruction.indexOf("(") + 1,
                    instruction.indexOf(")")
                ));
            String[] operants = operantSubstring.split(",");
            int firstOperant = Integer.parseInt(operants[0]);
            int secondOperant = Integer.parseInt(operants[1]);

            //perform multiplication and add to total sum
            sumOfMultiplications += firstOperant * secondOperant;
        }
        //output
        System.out.println(
            "The total sum of all multiplications is: " + sumOfMultiplications
        );
    }

    private static String inputToString(String filename) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
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
