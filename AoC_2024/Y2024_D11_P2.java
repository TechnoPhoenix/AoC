import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Y2024_D11_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/11");

        //split input into multiple stones
        String[] stonesArr = input.split(" ");

        //Hashmap key indicates stone number, value how often it is present
        HashMap<Long, Long> counter = new HashMap<>();
        for (int i = 0; i < stonesArr.length; i++) {
            counter.put(Long.parseLong(stonesArr[i]), 1l);
        }

        //blink 75 times
        for (int i = 0; i < 75; i++) {
            HashMap<Long, Long> updatedCounter = new HashMap<Long, Long>();
            for (Map.Entry<Long, Long> element : counter.entrySet()) {
                ArrayList<Long> newStones = blink(element.getKey());
                for (Long stone : newStones) {
                    updatedCounter.put(
                        stone,
                        updatedCounter.getOrDefault(stone, 0l) +
                            element.getValue()
                    );
                }
            }
            counter = updatedCounter;
        }

        long numberOfStones = 0;

        for (Map.Entry<Long, Long> element : counter.entrySet()) {
            numberOfStones += element.getValue();
        }

        System.out.println(
            "The number of stones after blinking 75 times is: " + numberOfStones
        );
    }

    private static ArrayList<Long> blink(Long initialStone) {
        ArrayList<Long> newStones = new ArrayList<Long>();
        if (initialStone.longValue() == 0) {
            newStones.add(Long.valueOf(1));
        } else if ((initialStone.toString().length() % 2) == 0) {
            //split into two stones
            newStones.add(
                Long.parseLong(
                    initialStone
                        .toString()
                        .substring(0, initialStone.toString().length() / 2)
                )
            );
            newStones.add(
                Long.parseLong(
                    initialStone
                        .toString()
                        .substring(
                            initialStone.toString().length() / 2,
                            initialStone.toString().length()
                        )
                )
            );
        } else {
            long temp = 2024 * (initialStone);
            newStones.add(temp);
        }
        return newStones;
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
