import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Y2024_D22_P2 {

    private record Sequence(long c1, long c2, long c3, long c4) {}

    static HashMap<Sequence, Long> priceMap;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/22");

        //split into different buyers inital numbers
        String[] initialString = input.split("\n");
        long[][] numbers = new long[initialString.length][2001];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i][0] = Long.parseLong(initialString[i].trim());
        }

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 1; j < numbers[i].length; j++) {
                long number = numbers[i][j - 1];
                number = (number ^ (number * 64)) % 16777216;
                number = (number ^ (number / 32)) % 16777216;
                number = (number ^ (number * 2048)) % 16777216;
                numbers[i][j] = number;
            }
        }

        long[][] prices = new long[numbers.length][numbers[0].length];
        for (int i = 0; i < prices.length; i++) {
            for (int j = 0; j < prices[0].length; j++) {
                prices[i][j] = numbers[i][j] % 10;
            }
        }
        priceMap = new HashMap<Sequence, Long>();
        for (int i = 0; i < prices.length; i++) {
            HashSet<Sequence> unavailable = new HashSet<Sequence>();
            for (int j = 4; j < prices[i].length; j++) {
                long c1 = prices[i][j - 0] - prices[i][j - 1];
                long c2 = prices[i][j - 1] - prices[i][j - 2];
                long c3 = prices[i][j - 2] - prices[i][j - 3];
                long c4 = prices[i][j - 3] - prices[i][j - 4];
                Sequence seq = new Sequence(c1, c2, c3, c4);
                if (!unavailable.contains(seq)) {
                    long price = priceMap.getOrDefault(seq, 0l);
                    price += prices[i][j];
                    priceMap.put(seq, price);
                    unavailable.add(seq);
                }
            }
        }

        long max = 0;
        for (Map.Entry<Sequence, Long> element : priceMap.entrySet()) {
            if (element.getValue() > max) {
                max = element.getValue();
            }
        }

        //output
        System.out.println("The most bananas we can get is: " + max);
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
