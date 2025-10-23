import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D14_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/14");

        String[] robots = input.split("\n");

        //calculate position of robots after n seconds
        int passedSeconds = 0;
        while (passedSeconds < 100000) {
            int[][] robotPositions = new int[101][103];

            for (String robot : robots) {
                String position = robot.split(" ")[0];
                String velocity = robot.split(" ")[1];

                int xPos = Integer.parseInt(
                    position.substring(2, position.length()).split(",")[0]
                );
                int yPos = Integer.parseInt(
                    position.substring(2, position.length()).split(",")[1]
                );

                int xVel = Integer.parseInt(
                    velocity.substring(2, velocity.length()).split(",")[0]
                );
                int yVel = Integer.parseInt(
                    velocity.substring(2, velocity.length()).split(",")[1]
                );

                int xPosEnd =
                    (((xPos + (passedSeconds * xVel)) % robotPositions.length) +
                        robotPositions.length) %
                    robotPositions.length;
                int yPosEnd =
                    (((yPos + (passedSeconds * yVel)) %
                            robotPositions[0].length) +
                        robotPositions[0].length) %
                    robotPositions[0].length;

                robotPositions[xPosEnd][yPosEnd]++;
            }

            //check if the robots create an image of a christmas tree (aka check for long line robots)
            boolean found = false;
            for (int i = 0; i < robotPositions.length; i++) {
                int lineCounter = 0;
                for (int j = 0; j < robotPositions[0].length; j++) {
                    if (robotPositions[i][j] != 0) {
                        lineCounter++;
                    } else {
                        lineCounter = 0;
                    }

                    if (lineCounter > 10) {
                        found = true;
                    }
                }
            }

            if (found) {
                //tree detected, print robot positions
                for (int i = 0; i < robotPositions.length; i++) {
                    for (int j = 0; j < robotPositions[0].length; j++) {
                        if (robotPositions[i][j] != 0) {
                            System.out.print("" + robotPositions[i][j]);
                        } else {
                            System.out.print(".");
                        }
                    }
                    System.out.println("");
                }
                System.out.println(
                    "Tree found after " + passedSeconds + " seconds"
                );
                break;
            }

            //increment counter of seconds
            passedSeconds++;
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
