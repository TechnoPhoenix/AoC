import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D15_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/15");

        String mapString = input.split("\n\n")[0];
        String moveSequence = input.split("\n\n")[1].trim();

        int xPos = 0;
        int yPos = 0;

        //turn map into 2d char array
        String[] mapLines = mapString.split("\n");
        char[][] map = new char[mapLines[0].length()][mapLines.length];
        for (int i = 0; i < mapLines[0].length(); i++) {
            for (int j = 0; j < mapLines.length; j++) {
                map[i][j] = mapLines[j].charAt(i);
                if (map[i][j] == '@') {
                    xPos = i;
                    yPos = j;
                    map[i][j] = '.';
                }
            }
        }

        //iterate over sequence of moves
        for (int i = 0; i < moveSequence.length(); i++) {
            switch (moveSequence.charAt(i)) {
                case '^':
                    if (map[xPos][yPos - 1] == '.') {
                        yPos--;
                    } else if (map[xPos][yPos - 1] == 'O') {
                        int offset = 2;
                        while (map[xPos][yPos - offset] != '#') {
                            if (map[xPos][yPos - offset] == '.') {
                                map[xPos][yPos - offset] = 'O';
                                map[xPos][yPos - 1] = '.';
                                yPos--;
                                break;
                            }
                            offset++;
                        }
                    }
                    break;
                case 'v':
                    if (map[xPos][yPos + 1] == '.') {
                        yPos++;
                    } else if (map[xPos][yPos + 1] == 'O') {
                        int offset = 2;
                        while (map[xPos][yPos + offset] != '#') {
                            if (map[xPos][yPos + offset] == '.') {
                                map[xPos][yPos + offset] = 'O';
                                map[xPos][yPos + 1] = '.';
                                yPos++;
                                break;
                            }
                            offset++;
                        }
                    }
                    break;
                case '<':
                    if (map[xPos - 1][yPos] == '.') {
                        xPos--;
                    } else if (map[xPos - 1][yPos] == 'O') {
                        int offset = 2;
                        while (map[xPos - offset][yPos] != '#') {
                            if (map[xPos - offset][yPos] == '.') {
                                map[xPos - offset][yPos] = 'O';
                                map[xPos - 1][yPos] = '.';
                                xPos--;
                                break;
                            }
                            offset++;
                        }
                    }
                    break;
                case '>':
                    if (map[xPos + 1][yPos] == '.') {
                        xPos++;
                    } else if (map[xPos + 1][yPos] == 'O') {
                        int offset = 2;
                        while (map[xPos + offset][yPos] != '#') {
                            if (map[xPos + offset][yPos] == '.') {
                                map[xPos + offset][yPos] = 'O';
                                map[xPos + 1][yPos] = '.';
                                xPos++;
                                break;
                            }
                            offset++;
                        }
                    }
                    break;
                default:
                    System.out.println("Unkown direction");
            }
        }

        //calculate sum of GPS coordinatea
        int coordinateSum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'O') {
                    coordinateSum += (100 * j) + i;
                }
            }
        }

        System.out.println(
            "The sum of all boxes GPS coordinates is: " + coordinateSum
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
