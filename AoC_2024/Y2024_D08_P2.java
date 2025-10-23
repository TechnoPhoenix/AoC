import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D08_P2 {

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

                        for (int k = 0; k < map.length * map[0].length; k++) {
                            for (int l = -55; l <= 55; l++) {
                                int xLoc = l * xDiff + (i % map[0].length);
                                int yLoc = l * yDiff + i / map.length;
                                if (
                                    (k % map[0].length) == xLoc &&
                                    (k / map.length) == yLoc
                                ) {
                                    if (
                                        xLoc < locations[0].length &&
                                        xLoc >= 0 &&
                                        yLoc < locations.length &&
                                        yLoc >= 0
                                    ) {
                                        locations[yLoc][xLoc] = true;
                                    }
                                }
                            }
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
