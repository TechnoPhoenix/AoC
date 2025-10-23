import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D14_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/14");

        String[] robots = input.split("\n");

        int[][] robotCount = new int[101][103];

        //calculate ending position for each robot
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
                (((xPos + (100 * xVel)) % robotCount.length) +
                    robotCount.length) %
                robotCount.length;
            int yPosEnd =
                (((yPos + (100 * yVel)) % robotCount[0].length) +
                    robotCount[0].length) %
                robotCount[0].length;

            robotCount[xPosEnd][yPosEnd]++;
        }

        //number of robots in each quadrant
        int NWQ = 0;
        int NEQ = 0;
        int SEQ = 0;
        int SWQ = 0;
        for (int i = 0; i < (robotCount.length / 2); i++) {
            for (int j = 0; j < (robotCount[0].length / 2); j++) {
                NWQ += robotCount[i][j];
            }
        }
        for (
            int i = ((robotCount.length / 2) + 1);
            i < robotCount.length;
            i++
        ) {
            for (int j = 0; j < (robotCount[0].length / 2); j++) {
                NEQ += robotCount[i][j];
            }
        }
        for (
            int i = ((robotCount.length / 2) + 1);
            i < robotCount.length;
            i++
        ) {
            for (
                int j = ((robotCount[0].length / 2) + 1);
                j < robotCount[0].length;
                j++
            ) {
                SEQ += robotCount[i][j];
            }
        }
        for (int i = 0; i < (robotCount.length / 2); i++) {
            for (
                int j = ((robotCount[0].length / 2) + 1);
                j < robotCount[0].length;
                j++
            ) {
                SWQ += robotCount[i][j];
            }
        }

        int safetyFactor = NWQ * NEQ * SEQ * SWQ;

        System.out.println("The safety factor is: " + safetyFactor);
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
