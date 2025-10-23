import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Y2024_D18_P1 {

    private record Coords(int xPos, int yPos) {}

    private record Step(Coords coords, int steps) {}

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/18");

        //spit input into coords
        String[] byteLocations = input.split("\n");
        Coords[] bytes = new Coords[byteLocations.length];
        for (int i = 0; i < bytes.length; i++) {
            int xPos = Integer.parseInt(byteLocations[i].split(",")[0]);
            int yPos = Integer.parseInt(byteLocations[i].split(",")[1]);
            bytes[i] = new Coords(xPos, yPos);
        }

        //create map with all spaces being safe
        char[][] map = new char[71][71];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = '.';
            }
        }

        //apply coordinates of first 1024 bytes falling onto the map
        for (int i = 0; i < 1024; i++) {
            map[bytes[i].xPos][bytes[i].yPos] = '#';
        }

        HashMap<Coords, Integer> locationSteps = new HashMap<Coords, Integer>();
        Step start = new Step(new Coords(0, 0), 0);
        Coords destination = new Coords(70, 70);

        //BFS
        Queue<Step> queue = new LinkedList<Step>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Step element = queue.poll();

            if (
                element.coords.xPos < 0 ||
                element.coords.xPos > 70 ||
                element.coords.yPos < 0 ||
                element.coords.yPos > 70
            ) {
                continue;
            }
            if (map[element.coords.xPos][element.coords.yPos] == '#') {
                continue;
            }
            if (
                element.steps <
                locationSteps
                    .getOrDefault(element.coords, Integer.MAX_VALUE)
                    .intValue()
            ) {
                locationSteps.put(element.coords, element.steps);
            } else {
                continue;
            }
            if (element.coords.equals(destination)) {
                continue;
            }

            //add adjacent tiles to queue
            queue.add(
                new Step(
                    new Coords(element.coords.xPos, element.coords.yPos - 1),
                    element.steps + 1
                )
            );
            queue.add(
                new Step(
                    new Coords(element.coords.xPos, element.coords.yPos + 1),
                    element.steps + 1
                )
            );
            queue.add(
                new Step(
                    new Coords(element.coords.xPos - 1, element.coords.yPos),
                    element.steps + 1
                )
            );
            queue.add(
                new Step(
                    new Coords(element.coords.xPos + 1, element.coords.yPos),
                    element.steps + 1
                )
            );
        }
        int leastSteps = locationSteps.get(new Coords(70, 70));

        //output
        System.out.println(
            "The least amount of steps that can be taken is: " + leastSteps
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
