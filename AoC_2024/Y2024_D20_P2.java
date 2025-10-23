import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Y2024_D20_P2 {

    private record Coords(int xPos, int yPos) {}

    static HashMap<Coords, Integer> track;
    static HashMap<Coords, Integer> iterationCache;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/20");

        int xStart = -1;
        int yStart = -1;
        int xEnd = -1;
        int yEnd = -1;
        //turn input into 2d char array
        String[] lines = input.split("\n");
        char[][] map = new char[lines[0].length()][lines.length];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[0].length(); j++) {
                map[j][i] = lines[i].charAt(j);
                if (map[j][i] == 'S') {
                    xStart = j;
                    yStart = i;
                    map[j][i] = '.';
                }
                if (map[j][i] == 'E') {
                    xEnd = j;
                    yEnd = i;
                    map[j][i] = '.';
                }
            }
        }

        int xPos = xStart;
        int yPos = yStart;
        int iteration = 0;
        char direction = 'E';
        //transfer track into hashmap
        track = new HashMap<Coords, Integer>();
        while (xPos != xEnd || yPos != yEnd) {
            track.put(new Coords(xPos, yPos), Integer.valueOf(iteration));
            iteration++;
            switch (direction) {
                case 'N':
                    if (map[xPos][yPos - 1] == '.') {
                        yPos--;
                        break;
                    }
                    if (map[xPos - 1][yPos] == '.') {
                        xPos--;
                        direction = 'W';
                        break;
                    }
                    if (map[xPos + 1][yPos] == '.') {
                        xPos++;
                        direction = 'E';
                        break;
                    }
                    System.out.println("No next tile found");
                case 'E':
                    if (map[xPos + 1][yPos] == '.') {
                        xPos++;
                        break;
                    }
                    if (map[xPos][yPos - 1] == '.') {
                        yPos--;
                        direction = 'N';
                        break;
                    }
                    if (map[xPos][yPos + 1] == '.') {
                        yPos++;
                        direction = 'S';
                        break;
                    }
                    System.out.println("No next tile found");
                case 'S':
                    if (map[xPos][yPos + 1] == '.') {
                        yPos++;
                        break;
                    }
                    if (map[xPos - 1][yPos] == '.') {
                        xPos--;
                        direction = 'W';
                        break;
                    }
                    if (map[xPos + 1][yPos] == '.') {
                        xPos++;
                        direction = 'E';
                        break;
                    }
                    System.out.println("No next tile found");
                case 'W':
                    if (map[xPos - 1][yPos] == '.') {
                        xPos--;
                        break;
                    }
                    if (map[xPos][yPos - 1] == '.') {
                        yPos--;
                        direction = 'N';
                        break;
                    }
                    if (map[xPos][yPos + 1] == '.') {
                        yPos++;
                        direction = 'S';
                        break;
                    }
                    System.out.println("No next tile found");
                default:
                    System.out.println("Unkown direction");
            }
        }
        track.put(new Coords(xPos, yPos), Integer.valueOf(iteration));

        int viableCheats = 0;
        //iterate over all tiles on the track
        for (Map.Entry<Coords, Integer> start : track.entrySet()) {
            for (Map.Entry<Coords, Integer> end : track.entrySet()) {
                int manhattenDistance = manhattenDistance(
                    start.getKey(),
                    end.getKey()
                );
                if (manhattenDistance <= 20) {
                    int cheatValue =
                        end.getValue() - start.getValue() - manhattenDistance;
                    if (cheatValue >= 100) {
                        viableCheats++;
                    }
                }
            }
        }

        //output
        System.out.println(
            "The number of cheats saving at least 100 picoseconds is: " +
                viableCheats
        );
    }

    private static int manhattenDistance(Coords start, Coords end) {
        int xDiff = Math.abs(start.xPos - end.xPos);
        int yDiff = Math.abs(start.yPos - end.yPos);
        return xDiff + yDiff;
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
