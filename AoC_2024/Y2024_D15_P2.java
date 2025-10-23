import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D15_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/15");

        String mapString = input.split("\n\n")[0];
        String moveSequence = input.split("\n\n")[1].trim();

        int xPos = 0;
        int yPos = 0;

        //turn map into 2d char array
        String[] mapLines = mapString.split("\n");
        char[][] previousMap = new char[mapLines[0].length()][mapLines.length];
        for (int i = 0; i < mapLines[0].length(); i++) {
            for (int j = 0; j < mapLines.length; j++) {
                previousMap[i][j] = mapLines[j].charAt(i);
            }
        }

        //make new map with changes form problem 2
        char[][] map = new char[previousMap.length * 2][previousMap[0].length];
        for (int i = 0; i < previousMap.length; i++) {
            for (int j = 0; j < previousMap[0].length; j++) {
                if (previousMap[i][j] == '#') {
                    map[i * 2][j] = '#';
                    map[i * 2 + 1][j] = '#';
                } else if (previousMap[i][j] == 'O') {
                    map[i * 2][j] = '[';
                    map[i * 2 + 1][j] = ']';
                } else if (previousMap[i][j] == '.') {
                    map[i * 2][j] = '.';
                    map[i * 2 + 1][j] = '.';
                } else if (previousMap[i][j] == '@') {
                    map[i * 2][j] = '.';
                    map[i * 2 + 1][j] = '.';
                    xPos = i * 2;
                    yPos = j;
                }
            }
        }

        //iterate over sequence of moves
        for (int i = 0; i < moveSequence.length(); i++) {
            char[][] prevMap = new char[map.length][map[0].length];
            for (int k = 0; k < map.length; k++) {
                for (int l = 0; l < map[0].length; l++) {
                    prevMap[k][l] = map[k][l];
                }
            }
            switch (moveSequence.charAt(i)) {
                case '^':
                    if (map[xPos][yPos - 1] == '.') {
                        yPos--;
                    } else if (map[xPos][yPos - 1] == '[') {
                        if (
                            shoveable(
                                map,
                                xPos,
                                yPos - 1,
                                moveSequence.charAt(i)
                            )
                        ) {
                            yPos--;
                        } else {
                            map = prevMap;
                        }
                    } else if (map[xPos][yPos - 1] == ']') {
                        if (
                            shoveable(
                                map,
                                xPos - 1,
                                yPos - 1,
                                moveSequence.charAt(i)
                            )
                        ) {
                            yPos--;
                        } else {
                            map = prevMap;
                        }
                    }
                    break;
                case 'v':
                    if (map[xPos][yPos + 1] == '.') {
                        yPos++;
                    } else if (map[xPos][yPos + 1] == '[') {
                        if (
                            shoveable(
                                map,
                                xPos,
                                yPos + 1,
                                moveSequence.charAt(i)
                            )
                        ) {
                            yPos++;
                        } else {
                            map = prevMap;
                        }
                    } else if (map[xPos][yPos + 1] == ']') {
                        if (
                            shoveable(
                                map,
                                xPos - 1,
                                yPos + 1,
                                moveSequence.charAt(i)
                            )
                        ) {
                            yPos++;
                        } else {
                            map = prevMap;
                        }
                    }
                    break;
                case '<':
                    if (map[xPos - 1][yPos] == '.') {
                        xPos--;
                    } else if (map[xPos - 1][yPos] == ']') {
                        if (
                            shoveable(
                                map,
                                xPos - 1,
                                yPos,
                                moveSequence.charAt(i)
                            )
                        ) {
                            xPos--;
                        } else {
                            map = prevMap;
                        }
                    }
                    break;
                case '>':
                    if (map[xPos + 1][yPos] == '.') {
                        xPos++;
                    } else if (map[xPos + 1][yPos] == '[') {
                        if (
                            shoveable(
                                map,
                                xPos + 1,
                                yPos,
                                moveSequence.charAt(i)
                            )
                        ) {
                            xPos++;
                        } else {
                            map = prevMap;
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
                if (map[i][j] == '[') {
                    coordinateSum += (100 * j) + i;
                }
            }
        }

        //output
        System.out.println(
            "The sum of all boxes GPS coordinates is: " + coordinateSum
        );
    }

    private static boolean shoveable(
        char[][] map,
        int xPos,
        int yPos,
        char direction
    ) {
        switch (direction) {
            case '^':
                if (
                    map[xPos][yPos - 1] == '.' && map[xPos + 1][yPos - 1] == '.'
                ) {
                    map[xPos][yPos - 1] = '[';
                    map[xPos + 1][yPos - 1] = ']';
                    map[xPos][yPos] = '.';
                    map[xPos + 1][yPos] = '.';
                    return true;
                } else if (map[xPos][yPos - 1] == '[') {
                    if (shoveable(map, xPos, yPos - 1, direction)) {
                        map[xPos][yPos - 1] = '[';
                        map[xPos + 1][yPos - 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else if (
                    map[xPos][yPos - 1] == ']' && map[xPos + 1][yPos - 1] == '.'
                ) {
                    if (shoveable(map, xPos - 1, yPos - 1, direction)) {
                        map[xPos][yPos - 1] = '[';
                        map[xPos + 1][yPos - 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else if (
                    map[xPos][yPos - 1] == '.' && map[xPos + 1][yPos - 1] == '['
                ) {
                    if (shoveable(map, xPos + 1, yPos - 1, direction)) {
                        map[xPos][yPos - 1] = '[';
                        map[xPos + 1][yPos - 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else if (
                    map[xPos][yPos - 1] == ']' && map[xPos + 1][yPos - 1] == '['
                ) {
                    if (
                        shoveable(map, xPos - 1, yPos - 1, direction) &&
                        shoveable(map, xPos + 1, yPos - 1, direction)
                    ) {
                        map[xPos][yPos - 1] = '[';
                        map[xPos + 1][yPos - 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case 'v':
                if (
                    map[xPos][yPos + 1] == '.' && map[xPos + 1][yPos + 1] == '.'
                ) {
                    map[xPos][yPos + 1] = '[';
                    map[xPos + 1][yPos + 1] = ']';
                    map[xPos][yPos] = '.';
                    map[xPos + 1][yPos] = '.';
                    return true;
                } else if (map[xPos][yPos + 1] == '[') {
                    if (shoveable(map, xPos, yPos + 1, direction)) {
                        map[xPos][yPos + 1] = '[';
                        map[xPos + 1][yPos + 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else if (
                    map[xPos][yPos + 1] == ']' && map[xPos + 1][yPos + 1] == '.'
                ) {
                    if (shoveable(map, xPos - 1, yPos + 1, direction)) {
                        map[xPos][yPos + 1] = '[';
                        map[xPos + 1][yPos + 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else if (
                    map[xPos][yPos + 1] == '.' && map[xPos + 1][yPos + 1] == '['
                ) {
                    if (shoveable(map, xPos + 1, yPos + 1, direction)) {
                        map[xPos][yPos + 1] = '[';
                        map[xPos + 1][yPos + 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else if (
                    map[xPos][yPos + 1] == ']' && map[xPos + 1][yPos + 1] == '['
                ) {
                    if (
                        shoveable(map, xPos - 1, yPos + 1, direction) &&
                        shoveable(map, xPos + 1, yPos + 1, direction)
                    ) {
                        map[xPos][yPos + 1] = '[';
                        map[xPos + 1][yPos + 1] = ']';
                        map[xPos][yPos] = '.';
                        map[xPos + 1][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case '<':
                if (map[xPos - 2][yPos] == '.') {
                    map[xPos - 2][yPos] = '[';
                    map[xPos - 1][yPos] = ']';
                    map[xPos][yPos] = '.';
                    return true;
                } else if (map[xPos - 2][yPos] == ']') {
                    if (shoveable(map, xPos - 2, yPos, direction)) {
                        map[xPos - 2][yPos] = '[';
                        map[xPos - 1][yPos] = ']';
                        map[xPos][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case '>':
                if (map[xPos + 2][yPos] == '.') {
                    map[xPos + 2][yPos] = ']';
                    map[xPos + 1][yPos] = '[';
                    map[xPos][yPos] = '.';
                    return true;
                } else if (map[xPos + 2][yPos] == '[') {
                    if (shoveable(map, xPos + 2, yPos, direction)) {
                        map[xPos + 2][yPos] = ']';
                        map[xPos + 1][yPos] = '[';
                        map[xPos][yPos] = '.';
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            default:
                System.out.println("Unkown direction");
                return false;
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
