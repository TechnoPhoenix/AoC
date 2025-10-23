import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Y2024_D09_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/9");

        //create full disk layout
        ArrayList<Integer> disk = new ArrayList<Integer>();
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < Integer.parseInt("" + input.charAt(i)); j++) {
                if (i % 2 == 0) {
                    disk.add(Integer.parseInt("" + i / 2));
                } else {
                    disk.add(-1);
                }
            }
        }

        //remove trailing empty blocks
        while (disk.get(disk.size() - 1) == -1) {
            disk.remove(disk.size() - 1);
        }

        //move fileblocks from the right into empty fileblocks from the left
        while (disk.indexOf(-1) != -1) {
            int emptyIndex = disk.indexOf(-1);
            disk.set(emptyIndex, disk.get(disk.size() - 1));
            disk.remove(disk.size() - 1);
        }

        //calculate checksum
        long checkSum = 0;
        for (int i = 0; i < disk.size(); i++) {
            checkSum += disk.get(i) * i;
        }

        System.out.println("The checksum of the reduced disk is: " + checkSum);
    }

    private static String inputToString(String filename) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
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
