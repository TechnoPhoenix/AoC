import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D10_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/10");

        //turn input string into 2d int array
        String[] rows = input.split("\n");
        int[][] map = new int[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                map[i][j] = Integer.parseInt("" + rows[i].charAt(j));
            }
        }

        int totalRating = 0;

        //iterate over map finding all trailheads
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    //trailhead is present, determine its score and add to total score
                    totalRating += calcRating(i, j, map);
                }
            }
        }

        System.out.println(
            "The sum of the scores of all trailheads is: " + totalRating
        );
    }

    private static int calcRating(int i, int j, int[][] map) {
        int result = 0;

        result += recTraverse(i - 1, j, map, 1); //UP
        result += recTraverse(i + 1, j, map, 1); //DOWN
        result += recTraverse(i, j - 1, map, 1); //LEFT
        result += recTraverse(i, j + 1, map, 1); //RIGHT

        return result;
    }

    private static int recTraverse(int i, int j, int[][] map, int reqHeight) {
        int result = 0;
        try {
            if (reqHeight == 9) {
                if (map[i][j] == 9) {
                    return 1;
                }
            } else {
                if (map[i][j] == reqHeight) {
                    result += recTraverse(i - 1, j, map, reqHeight + 1); //UP
                    result += recTraverse(i + 1, j, map, reqHeight + 1); //DOWN
                    result += recTraverse(i, j - 1, map, reqHeight + 1); //LEFT
                    result += recTraverse(i, j + 1, map, reqHeight + 1); //RIGHT
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
        return result;
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
