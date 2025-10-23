import java.io.FileReader;

public class Y2024_D04_P2 {

    public static void main(String[] args) {
        //read input file into a two dimensional array of chars
        char[][] input = inputToCharArray("/home/user/AoC_Inputs/2024/4");

        int wordCounter = 0;

        //check for X shaped MAS
        for (int i = 0; i < (input.length - 2); i++) {
            for (int j = 0; j < (input.length - 2); j++) {
                String temp1 =
                    "" +
                    input[i + 0][j + 0] +
                    input[i + 1][j + 1] +
                    input[i + 2][j + 2];
                String temp2 =
                    "" +
                    input[i + 2][j + 2] +
                    input[i + 1][j + 1] +
                    input[i + 0][j + 0];
                String temp3 =
                    "" +
                    input[i + 2][j + 0] +
                    input[i + 1][j + 1] +
                    input[i + 0][j + 2];
                String temp4 =
                    "" +
                    input[i + 0][j + 2] +
                    input[i + 1][j + 1] +
                    input[i + 2][j + 0];
                if (
                    (temp1.equalsIgnoreCase("MAS") ||
                        temp2.equalsIgnoreCase("MAS")) &&
                    (temp3.equalsIgnoreCase("MAS") ||
                        temp4.equalsIgnoreCase("MAS"))
                ) {
                    wordCounter++;
                }
            }
        }

        //output
        System.out.println(
            "The total number of MAS in X shape in the input is: " + wordCounter
        );
    }

    private static char[][] inputToCharArray(String filename) {
        char[][] result = new char[140][140];
        try (FileReader fr = new FileReader(filename)) {
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    result[i][j] = (char) fr.read();
                }
                fr.read();
                fr.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
