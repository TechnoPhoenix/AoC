import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Y2024_D23_P1 {

    private record Trio(String a, String b, String c) {}

    static HashMap<String, HashSet<String>> graph;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/23");

        //convert input into graph
        graph = new HashMap<String, HashSet<String>>();
        for (String str : input.split("\n")) {
            String a = str.trim().split("-")[0];
            String b = str.trim().split("-")[1];
            HashSet<String> aSet = graph.getOrDefault(a, new HashSet<String>());
            HashSet<String> bSet = graph.getOrDefault(b, new HashSet<String>());
            aSet.add(b);
            bSet.add(a);
            graph.put(a, aSet);
            graph.put(b, bSet);
        }

        //search for sets of three interconnected computers
        HashSet<Trio> trios = new HashSet<Trio>();
        for (String vertex1 : graph.keySet()) {
            for (String vertex2 : graph.get(vertex1)) {
                for (String vertex3 : graph.get(vertex2)) {
                    if (graph.get(vertex3).contains(vertex1)) {
                        trios.add(new Trio(vertex1, vertex2, vertex3));
                    }
                }
            }
        }

        int counter = 0;
        for (Trio element : trios) {
            if (
                element.a.charAt(0) == 't' ||
                element.b.charAt(0) == 't' ||
                element.c.charAt(0) == 't'
            ) {
                counter++;
            }
        }
        counter /= 6;

        //output
        System.out.println(
            "The number of sets with at least one computer starting with the letter t is: " +
                counter
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
