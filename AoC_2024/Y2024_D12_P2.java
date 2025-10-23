import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Y2024_D12_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/12");

        //turn input string into 2d char array
        String[] rows = input.split("\n");
        char[][] map = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                map[i][j] = rows[i].charAt(j);
            }
        }

        HashMap<boolean[][], Integer[]> regions = new HashMap<
            boolean[][],
            Integer[]
        >();

        //identify regions
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                //identify region of this tile
                boolean[][] currentRegion = identifyRegion(map, i, j);
                boolean duplicate = false;
                for (Map.Entry<
                    boolean[][],
                    Integer[]
                > element : regions.entrySet()) {
                    if (Arrays.deepEquals(currentRegion, element.getKey())) {
                        duplicate = true;
                    }
                }
                if (!duplicate) {
                    regions.put(currentRegion, null);
                }
            }
        }

        //identify region area and number of sides
        for (Map.Entry<boolean[][], Integer[]> element : regions.entrySet()) {
            //area
            int area = 0;
            for (int i = 0; i < element.getKey().length; i++) {
                for (int j = 0; j < element.getKey()[0].length; j++) {
                    if (element.getKey()[i][j]) {
                        area++;
                    }
                }
            }

            //number of sides
            int numberOfSides = calcNumberOfSides(element.getKey());

            Integer[] value = {
                Integer.valueOf(area),
                Integer.valueOf(numberOfSides),
            };
            element.setValue(value);
        }

        int totalCost = 0;

        for (Map.Entry<boolean[][], Integer[]> element : regions.entrySet()) {
            totalCost += element.getValue()[0] * element.getValue()[1];
        }

        System.out.println(
            "The total cost of fencing all regions is: " + totalCost
        );
    }

    private static int calcNumberOfSides(boolean[][] region) {
        int result = 0;

        boolean[][] fencesUp = new boolean[region.length][region[0].length];
        boolean[][] fencesDown = new boolean[region.length][region[0].length];
        boolean[][] fencesLeft = new boolean[region.length][region[0].length];
        boolean[][] fencesRight = new boolean[region.length][region[0].length];

        for (int i = 0; i < region.length; i++) {
            for (int j = 0; j < region[0].length; j++) {
                if (region[i][j]) {
                    try {
                        if (!region[i - 1][j]) {
                            fencesUp[i][j] = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        fencesUp[i][j] = true;
                    }
                    try {
                        if (!region[i + 1][j]) {
                            fencesDown[i][j] = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        fencesDown[i][j] = true;
                    }
                    try {
                        if (!region[i][j - 1]) {
                            fencesLeft[i][j] = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        fencesLeft[i][j] = true;
                    }
                    try {
                        if (!region[i][j + 1]) {
                            fencesRight[i][j] = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        fencesRight[i][j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < fencesUp.length; i++) {
            for (int j = 0; j < fencesUp[0].length; j++) {
                try {
                    if (fencesUp[i][j] && !fencesUp[i][j - 1]) {
                        result++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    result++;
                }
            }
        }
        for (int i = 0; i < fencesDown.length; i++) {
            for (int j = 0; j < fencesDown[0].length; j++) {
                try {
                    if (fencesDown[i][j] && !fencesDown[i][j - 1]) {
                        result++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    result++;
                }
            }
        }
        for (int j = 0; j < fencesLeft[0].length; j++) {
            for (int i = 0; i < fencesLeft.length; i++) {
                try {
                    if (fencesLeft[i][j] && !fencesLeft[i - 1][j]) {
                        result++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    result++;
                }
            }
        }
        for (int i = 0; i < fencesRight.length; i++) {
            for (int j = 0; j < fencesRight[0].length; j++) {
                try {
                    if (fencesRight[i][j] && !fencesRight[i - 1][j]) {
                        result++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    result++;
                }
            }
        }

        return result;
    }

    private static boolean[][] identifyRegion(char[][] map, int x, int y) {
        boolean[][] currentRegion = new boolean[map.length][map[0].length];
        currentRegion[x][y] = true;
        char origin = map[x][y];

        recIdentify(map, currentRegion, x - 1, y, origin); //UP
        recIdentify(map, currentRegion, x + 1, y, origin); //DOWN
        recIdentify(map, currentRegion, x, y - 1, origin); //LEFT
        recIdentify(map, currentRegion, x, y + 1, origin); //RIGHT

        return currentRegion;
    }

    private static void recIdentify(
        char[][] map,
        boolean[][] currentRegion,
        int x,
        int y,
        char origin
    ) {
        try {
            if (currentRegion[x][y] == true) {
                return;
            } else if (map[x][y] == origin) {
                currentRegion[x][y] = true;

                recIdentify(map, currentRegion, x - 1, y, origin); //UP
                recIdentify(map, currentRegion, x + 1, y, origin); //DOWN
                recIdentify(map, currentRegion, x, y - 1, origin); //LEFT
                recIdentify(map, currentRegion, x, y + 1, origin); //RIGHT
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
