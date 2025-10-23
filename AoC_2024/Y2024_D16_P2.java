import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Y2024_D16_P2 {

    private record Coords(int xPos, int yPos) {}

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    static HashMap<Coords, Integer> reachedLocations;
    static HashMap<boolean[][], Integer> possiblePaths;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/16");

        int xPos = -1;
        int yPos = -1;
        //turn input into 2d char array
        String[] lines = input.split("\n");
        char[][] map = new char[lines[0].length()][lines.length];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[0].length(); j++) {
                map[j][i] = lines[i].charAt(j);
                if (map[j][i] == 'S') {
                    xPos = j;
                    yPos = i;
                }
            }
        }

        //flood dead ends
        for (int i = 1; i < (map.length - 1); i++) {
            for (int j = 1; j < (map[0].length - 1); j++) {
                if (map[i][j] == '.') {
                    int ways = 0;
                    if (map[i - 1][j] != '#') {
                        ways++;
                    }
                    if (map[i + 1][j] != '#') {
                        ways++;
                    }
                    if (map[i][j - 1] != '#') {
                        ways++;
                    }
                    if (map[i][j + 1] != '#') {
                        ways++;
                    }

                    if (ways == 1) {
                        map[i][j] = '#';
                        flood(map, i - 1, j);
                        flood(map, i + 1, j);
                        flood(map, i, j - 1);
                        flood(map, i, j + 1);
                    }
                }
            }
        }

        boolean[][] preVisited = new boolean[map.length][map[0].length];
        for (int i = 0; i < preVisited.length; i++) {
            for (int j = 0; j < preVisited[0].length; j++) {
                preVisited[i][j] = false;
            }
        }
        preVisited[xPos][yPos] = true;

        reachedLocations = new HashMap<Coords, Integer>();
        possiblePaths = new HashMap<boolean[][], Integer>();

        int north = traverseMap(
            map,
            1001,
            preVisited,
            xPos,
            yPos - 1,
            Direction.NORTH
        );
        int south = traverseMap(
            map,
            1001,
            preVisited,
            xPos,
            yPos + 1,
            Direction.SOUTH
        );
        int east = traverseMap(
            map,
            0001,
            preVisited,
            xPos + 1,
            yPos,
            Direction.EAST
        );
        int west = traverseMap(
            map,
            2001,
            preVisited,
            xPos - 1,
            yPos,
            Direction.WEST
        );
        int lowestScore = Math.min(
            Math.min(north, south),
            Math.min(east, west)
        );

        //mark all tiles which are part of an optimal solution
        boolean[][] partOfPath = new boolean[map.length][map[0].length];
        for (int i = 0; i < partOfPath.length; i++) {
            for (int j = 0; j < partOfPath[0].length; j++) {
                partOfPath[i][j] = false;
            }
        }
        for (Map.Entry<
            boolean[][],
            Integer
        > element : possiblePaths.entrySet()) {
            if (element.getValue().intValue() == lowestScore) {
                for (int i = 0; i < partOfPath.length; i++) {
                    for (int j = 0; j < partOfPath[0].length; j++) {
                        if (element.getKey()[i][j]) {
                            partOfPath[i][j] = true;
                        }
                    }
                }
            }
        }

        //count number of tiles in optimal paths
        int tileCount = 0;
        for (int i = 0; i < partOfPath.length; i++) {
            for (int j = 0; j < partOfPath[0].length; j++) {
                if (partOfPath[i][j]) {
                    tileCount++;
                }
            }
        }

        //output
        System.out.println(
            "The number of tiles in all optimal paths is: " + tileCount
        );
    }

    private static int traverseMap(
        char[][] map,
        int currentScore,
        boolean[][] oldPreVisited,
        int xPos,
        int yPos,
        Direction direction
    ) {
        Coords coords = new Coords(xPos, yPos);
        if (
            reachedLocations
                .getOrDefault(coords, Integer.MAX_VALUE)
                .intValue() <
            (currentScore - 1000)
        ) {
            return Integer.MAX_VALUE;
        } else if (
            reachedLocations
                .getOrDefault(coords, Integer.MAX_VALUE)
                .intValue() >
            (currentScore)
        ) {
            reachedLocations.put(coords, Integer.valueOf(currentScore));
        }

        boolean[][] preVisited =
            new boolean[oldPreVisited.length][oldPreVisited[0].length];
        for (int i = 0; i < preVisited.length; i++) {
            for (int j = 0; j < preVisited[0].length; j++) {
                preVisited[i][j] = oldPreVisited[i][j];
            }
        }
        int result = Integer.MAX_VALUE;
        int forward = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        if (map[xPos][yPos] == '#' || preVisited[xPos][yPos]) {
            return result;
        } else if (map[xPos][yPos] == 'E') {
            preVisited[xPos][yPos] = true;
            possiblePaths.put(preVisited, Integer.valueOf(currentScore));
            return currentScore;
        } else {
            preVisited[xPos][yPos] = true;
            switch (direction) {
                case NORTH:
                    forward = traverseMap(
                        map,
                        currentScore + 1,
                        preVisited,
                        xPos,
                        yPos - 1,
                        Direction.NORTH
                    );
                    right = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos + 1,
                        yPos,
                        Direction.EAST
                    );
                    left = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos - 1,
                        yPos,
                        Direction.WEST
                    );

                    result = Math.min(Math.min(right, left), forward);
                    break;
                case EAST:
                    forward = traverseMap(
                        map,
                        currentScore + 1,
                        preVisited,
                        xPos + 1,
                        yPos,
                        Direction.EAST
                    );
                    right = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos,
                        yPos + 1,
                        Direction.SOUTH
                    );
                    left = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos,
                        yPos - 1,
                        Direction.NORTH
                    );

                    result = Math.min(Math.min(right, left), forward);
                    break;
                case SOUTH:
                    forward = traverseMap(
                        map,
                        currentScore + 1,
                        preVisited,
                        xPos,
                        yPos + 1,
                        Direction.SOUTH
                    );
                    right = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos - 1,
                        yPos,
                        Direction.WEST
                    );
                    left = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos + 1,
                        yPos,
                        Direction.EAST
                    );

                    result = Math.min(Math.min(right, left), forward);
                    break;
                case WEST:
                    forward = traverseMap(
                        map,
                        currentScore + 1,
                        preVisited,
                        xPos - 1,
                        yPos,
                        Direction.WEST
                    );
                    right = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos,
                        yPos - 1,
                        Direction.NORTH
                    );
                    left = traverseMap(
                        map,
                        currentScore + 1001,
                        preVisited,
                        xPos,
                        yPos + 1,
                        Direction.SOUTH
                    );

                    result = Math.min(Math.min(right, left), forward);
                    break;
                default:
                    System.out.println("Unkown direction");
                    return result;
            }
        }
        return result;
    }

    private static void flood(char[][] map, int i, int j) {
        if (map[i][j] == '.') {
            int ways = 0;
            if (map[i - 1][j] != '#') {
                ways++;
            }
            if (map[i + 1][j] != '#') {
                ways++;
            }
            if (map[i][j - 1] != '#') {
                ways++;
            }
            if (map[i][j + 1] != '#') {
                ways++;
            }

            if (ways == 1) {
                map[i][j] = '#';
                flood(map, i - 1, j);
                flood(map, i + 1, j);
                flood(map, i, j - 1);
                flood(map, i, j + 1);
            }
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
