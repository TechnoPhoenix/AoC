import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Y2024_D11_P1 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/11");

        //split input into multiple stones stored in array
        String[] stonesArr = input.split(" ");
        ArrayList<Long> stones = new ArrayList<Long>();
        for (int i = 0; i < stonesArr.length; i++) {
            stones.add(Long.parseLong(stonesArr[i]));
        }

        //blink 25 times
        for (int i = 0; i < 25; i++) {
            ArrayList<Long> newStones = new ArrayList<Long>();
            //iterate through list of stones and apply rules, storing new order in a temporary list
            for (int j = 0; j < stones.size(); j++) {
                Long current = stones.get(j);
                if (current.longValue() == 0) {
                    newStones.add(Long.valueOf(1));
                } else if ((current.toString().length() % 2) == 0) {
                    //split into two stones
                    newStones.add(
                        Long.parseLong(
                            current
                                .toString()
                                .substring(0, current.toString().length() / 2)
                        )
                    );
                    newStones.add(
                        Long.parseLong(
                            current
                                .toString()
                                .substring(
                                    current.toString().length() / 2,
                                    current.toString().length()
                                )
                        )
                    );
                } else {
                    long temp = 2024 * (current);
                    newStones.add(temp);
                }
            }
            stones = newStones;
        }

        System.out.println(
            "The number of stones after blinking 25 times is: " + stones.size()
        );
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
