import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D06_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/6");

        //position of guard
        int xpos = 0;
        int ypos = 0;
        String direction = "UP";

        //turn input string into 2d char array
        String[] rows = input.split("\n");
        char[][] map = new char[rows[0].length()][rows.length];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                map[j][i] = rows[j].charAt(i);
                if (map[j][i] == '^') {
                    xpos = i;
                    ypos = j;
                    map[j][i] = '.';
                }
            }
        }

        //iterate over every element and test if replacing it with a box would lead to an infinite loop
        int possibleLoopBoxes = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '.') {
                    char[][] tempMap = new char[map.length][map[0].length];
                    for (int k = 0; k < tempMap.length; k++) {
                        for (int l = 0; l < map[k].length; l++) {
                            tempMap[k][l] = map[k][l];
                        }
                    }
                    tempMap[i][j] = '#';
                    if (createsInfiniteLoop(xpos, ypos, direction, tempMap)) {
                        possibleLoopBoxes++;
                    }
                }
            }
        }

        //output
        System.out.println(
            "The number of possible box positions for infinite loops is: " +
                possibleLoopBoxes
        );
    }

    private static boolean createsInfiniteLoop(
        int startingXpos,
        int startingYpos,
        String startingDirection,
        char[][] map
    ) {
        int xpos = startingXpos;
        int ypos = startingYpos;
        String direction = startingDirection;
        int countdown = Short.MAX_VALUE;
        boolean exit = false;
        while (true) {
            if (countdown <= 0) {
                return true;
            }

            //adjust direction if boxes are in the way
            boolean adjusted = false;
            int rotations = 0;
            try {
                while (!adjusted) {
                    switch (direction) {
                        case "UP":
                            if (map[ypos - 1][xpos] == '#') {
                                direction = rotate(direction);
                            } else {
                                adjusted = true;
                            }
                            break;
                        case "DOWN":
                            if (map[ypos + 1][xpos] == '#') {
                                direction = rotate(direction);
                            } else {
                                adjusted = true;
                            }
                            break;
                        case "LEFT":
                            if (map[ypos][xpos - 1] == '#') {
                                direction = rotate(direction);
                            } else {
                                adjusted = true;
                            }
                            break;
                        case "RIGHT":
                            if (map[ypos][xpos + 1] == '#') {
                                direction = rotate(direction);
                            } else {
                                adjusted = true;
                            }
                            break;
                        default:
                            System.out.println("unkown direction");
                    }
                    rotations++;
                    if (rotations > 10) {
                        return true;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                exit = true;
            }

            //perfrom movement of the guard
            switch (direction) {
                case "UP":
                    ypos--;
                    break;
                case "DOWN":
                    ypos++;
                    break;
                case "LEFT":
                    xpos--;
                    break;
                case "RIGHT":
                    xpos++;
                    break;
                default:
                    System.out.println("unkown direction");
            }

            if (exit) {
                break;
            }

            countdown--;
        }

        return false;
    }

    private static String rotate(String direction) {
        switch (direction) {
            case "UP":
                return "RIGHT";
            case "DOWN":
                return "LEFT";
            case "LEFT":
                return "UP";
            case "RIGHT":
                return "DOWN";
            default:
                return "ERROR";
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
