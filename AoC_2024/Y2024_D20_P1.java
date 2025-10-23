import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Y2024_D20_P1 {

    private record Coords(int xPos, int yPos) {}

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
        HashMap<Coords, Integer> track = new HashMap<Coords, Integer>();
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
        //attempt to cheat for each wall
        for (int i = 1; i < (map.length - 1); i++) {
            for (int j = 1; j < (map[0].length - 1); j++) {
                if (map[i][j] == '#') {
                    if (map[i - 1][j] == '.' && map[i + 1][j] == '.') {
                        Coords a = new Coords(i - 1, j);
                        Coords b = new Coords(i + 1, j);
                        int diff =
                            track.get(a).intValue() - track.get(b).intValue();
                        diff = Math.abs(diff) - 2;
                        if (diff > 99) {
                            viableCheats++;
                        }
                    } else if (map[i][j - 1] == '.' && map[i][j + 1] == '.') {
                        Coords a = new Coords(i, j - 1);
                        Coords b = new Coords(i, j + 1);
                        int diff =
                            track.get(a).intValue() - track.get(b).intValue();
                        diff = Math.abs(diff) - 2;
                        if (diff > 99) {
                            viableCheats++;
                        }
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
