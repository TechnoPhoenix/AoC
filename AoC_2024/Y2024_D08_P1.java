import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D08_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/8");

        //turn input string into 2d char array
        String[] rows = input.split("\n");
        char[][] map = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                map[i][j] = rows[i].charAt(j);
            }
        }

        //2d bool array to save locations of antinodes
        boolean[][] locations = new boolean[map.length][map[0].length];

        //iterate over map and check for antennas of same type and calc location of antinode
        for (int i = 0; i < (map.length * map[0].length); i++) {
            if (map[i / map.length][i % map[0].length] != '.') {
                for (int j = i + 1; j < (map.length * map[0].length); j++) {
                    if (
                        map[i / map.length][i % map[0].length] ==
                        map[j / map.length][j % map[0].length]
                    ) {
                        //calculate difference of antennas
                        int xDiff = (j % map[0].length) - (i % map[0].length);
                        int yDiff = (j / map.length) - (i / map.length);

                        //first antinode
                        int x1 = (i % map[0].length) - xDiff;
                        int y1 = (i / map.length) - yDiff;
                        if (
                            x1 < locations[0].length &&
                            x1 >= 0 &&
                            y1 < locations.length &&
                            y1 >= 0
                        ) {
                            locations[y1][x1] = true;
                        }

                        //second antinode
                        int x2 = (j % map[0].length) + xDiff;
                        int y2 = (j / map.length) + yDiff;
                        if (
                            x2 < locations[0].length &&
                            x2 >= 0 &&
                            y2 < locations.length &&
                            y2 >= 0
                        ) {
                            locations[y2][x2] = true;
                        }
                    }
                }
            }
        }

        //count number of locations with antinode
        int locationCount = 0;
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                if (locations[i][j]) {
                    locationCount++;
                }
            }
        }

        System.out.println(
            "Total number of locations with antinodes is: " + locationCount
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
