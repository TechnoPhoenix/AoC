import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D06_P1 {

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
                }
            }
        }

        //remember visited places by boolean 2d array
        boolean[][] visited = new boolean[map.length][map[0].length];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited.length; j++) {
                visited[i][j] = false;
            }
        }

        //guard walking path and marking visited positions
        boolean exit = false;
        while (true) {
            visited[ypos][xpos] = true;
            try {
                switch (direction) {
                    case "UP":
                        ypos--;
                        if (map[ypos - 1][xpos] == '#') {
                            direction = "RIGHT";
                        }
                        break;
                    case "DOWN":
                        ypos++;
                        if (map[ypos + 1][xpos] == '#') {
                            direction = "LEFT";
                        }
                        break;
                    case "LEFT":
                        xpos--;
                        if (map[ypos][xpos - 1] == '#') {
                            direction = "UP";
                        }
                        break;
                    case "RIGHT":
                        xpos++;
                        if (map[ypos][xpos + 1] == '#') {
                            direction = "DOWN";
                        }
                        break;
                    default:
                        System.out.println("unkown direction");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                exit = true;
                visited[ypos][xpos] = true;
            }

            if (exit) {
                break;
            }
        }

        //count visited positions
        int visitedPositions = 0;
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (visited[i][j]) {
                    visitedPositions++;
                }
            }
        }

        //output
        System.out.println(
            "The number of positions the guard visited is: " + visitedPositions
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
