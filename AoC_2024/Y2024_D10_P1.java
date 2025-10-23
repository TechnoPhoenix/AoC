import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D10_P1 {

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

        int totalScore = 0;

        //iterate over map finding all trailheads
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    //trailhead is present, determine its score and add to total score
                    totalScore += calcScore(i, j, map);
                }
            }
        }

        System.out.println(
            "The sum of the scores of all trailheads is: " + totalScore
        );
    }

    private static int calcScore(int i, int j, int[][] map) {
        boolean[][] trailTails = new boolean[map.length][map[0].length];
        for (int k = 0; k < trailTails.length; k++) {
            for (int l = 0; l < trailTails[0].length; l++) {
                trailTails[i][j] = false;
            }
        }

        recTraverse(i - 1, j, map, 1, trailTails); //UP
        recTraverse(i + 1, j, map, 1, trailTails); //DOWN
        recTraverse(i, j - 1, map, 1, trailTails); //LEFT
        recTraverse(i, j + 1, map, 1, trailTails); //RIGHT

        int result = 0;
        for (int k = 0; k < trailTails.length; k++) {
            for (int l = 0; l < trailTails[0].length; l++) {
                if (trailTails[k][l]) {
                    result++;
                }
            }
        }
        return result;
    }

    private static void recTraverse(
        int i,
        int j,
        int[][] map,
        int reqHeight,
        boolean[][] trailTails
    ) {
        try {
            if (reqHeight == 9) {
                if (map[i][j] == 9) {
                    trailTails[i][j] = true;
                }
            } else {
                if (map[i][j] == reqHeight) {
                    recTraverse(i - 1, j, map, reqHeight + 1, trailTails); //UP
                    recTraverse(i + 1, j, map, reqHeight + 1, trailTails); //DOWN
                    recTraverse(i, j - 1, map, reqHeight + 1, trailTails); //LEFT
                    recTraverse(i, j + 1, map, reqHeight + 1, trailTails); //RIGHT
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
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
