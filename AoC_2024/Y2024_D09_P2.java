import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class Y2024_D09_P2 {

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

        //iterate trough list backwards, trying to find space for files earlier in the list
        int fileIndex = disk.get(disk.size() - 1);
        while (fileIndex > 0) {
            int index = disk.lastIndexOf(fileIndex);
            Stack<Integer> temp = new Stack<Integer>();
            temp.push(disk.get(index));
            disk.set(index, -2);
            index--;
            int fileSize = 1;
            while (
                (index >= 0) &&
                (temp.peek().intValue() == disk.get(index).intValue())
            ) {
                temp.push(disk.get(index));
                disk.set(index, -2);
                fileSize++;
                index--;
            }

            int gapIndex = findGap(disk, fileSize);

            if (gapIndex != -1 && gapIndex < index) {
                while (!temp.empty()) {
                    disk.set(gapIndex, temp.pop());
                    gapIndex++;
                }
            } else {
                index++;
                while (!temp.empty()) {
                    disk.set(index, temp.pop());
                    index++;
                }
            }

            for (int i = 0; i < disk.size(); i++) {
                if (disk.get(i) == -2) {
                    disk.set(i, -1);
                }
            }
            fileIndex--;
        }

        //calculate checksum
        long checkSum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) != -1) {
                checkSum += disk.get(i) * i;
            }
        }

        //output
        System.out.println("The checksum of the reduced disk is: " + checkSum);
    }

    private static int findGap(ArrayList<Integer> disk, int fileSize) {
        int gapSize = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == -1) {
                gapSize++;
            } else {
                gapSize = 0;
            }
            if (gapSize == fileSize) {
                return i - (gapSize - 1);
            }
        }
        return -1;
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
