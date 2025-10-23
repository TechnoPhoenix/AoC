import java.io.FileReader;

public class Y2024_D04_P1 {

    public static void main(String[] args) {
        //read input file into a two dimensional array of chars
        char[][] input = inputToCharArray("/home/user/AoC_Inputs/2024/4");

        int wordCounter = 0;

        //check horizontals
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < (input.length - 3); j++) {
                String temp1 =
                    "" +
                    input[i][j + 0] +
                    input[i][j + 1] +
                    input[i][j + 2] +
                    input[i][j + 3];
                String temp2 =
                    "" +
                    input[i][j + 3] +
                    input[i][j + 2] +
                    input[i][j + 1] +
                    input[i][j + 0];
                if (
                    temp1.equalsIgnoreCase("XMAS") ||
                    temp2.equalsIgnoreCase("XMAS")
                ) {
                    wordCounter++;
                }
            }
        }

        //check verticals
        for (int i = 0; i < (input.length - 3); i++) {
            for (int j = 0; j < input.length; j++) {
                String temp1 =
                    "" +
                    input[i + 0][j] +
                    input[i + 1][j] +
                    input[i + 2][j] +
                    input[i + 3][j];
                String temp2 =
                    "" +
                    input[i + 3][j] +
                    input[i + 2][j] +
                    input[i + 1][j] +
                    input[i + 0][j];
                if (
                    temp1.equalsIgnoreCase("XMAS") ||
                    temp2.equalsIgnoreCase("XMAS")
                ) {
                    wordCounter++;
                }
            }
        }

        //check diagonals
        for (int i = 0; i < (input.length - 3); i++) {
            for (int j = 0; j < (input.length - 3); j++) {
                String temp1 =
                    "" +
                    input[i + 0][j + 0] +
                    input[i + 1][j + 1] +
                    input[i + 2][j + 2] +
                    input[i + 3][j + 3];
                String temp2 =
                    "" +
                    input[i + 3][j + 3] +
                    input[i + 2][j + 2] +
                    input[i + 1][j + 1] +
                    input[i + 0][j + 0];
                if (
                    temp1.equalsIgnoreCase("XMAS") ||
                    temp2.equalsIgnoreCase("XMAS")
                ) {
                    wordCounter++;
                }
                String temp3 =
                    "" +
                    input[i + 3][j + 0] +
                    input[i + 2][j + 1] +
                    input[i + 1][j + 2] +
                    input[i + 0][j + 3];
                String temp4 =
                    "" +
                    input[i + 0][j + 3] +
                    input[i + 1][j + 2] +
                    input[i + 2][j + 1] +
                    input[i + 3][j + 0];
                if (
                    temp3.equalsIgnoreCase("XMAS") ||
                    temp4.equalsIgnoreCase("XMAS")
                ) {
                    wordCounter++;
                }
            }
        }

        //output
        System.out.println(
            "The total number of XMAS in the input is: " + wordCounter
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
