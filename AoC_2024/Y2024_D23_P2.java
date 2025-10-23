import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Y2024_D23_P2 {

    static HashMap<String, ArrayList<String>> graph;
    static ArrayList<ArrayList<String>> cliques;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/23");

        //convert input into graph
        graph = new HashMap<String, ArrayList<String>>();
        for (String str : input.split("\n")) {
            String a = str.trim().split("-")[0];
            String b = str.trim().split("-")[1];
            ArrayList<String> aSet = graph.getOrDefault(
                a,
                new ArrayList<String>()
            );
            ArrayList<String> bSet = graph.getOrDefault(
                b,
                new ArrayList<String>()
            );
            aSet.add(b);
            bSet.add(a);
            graph.put(a, aSet);
            graph.put(b, bSet);
        }

        cliques = new ArrayList<ArrayList<String>>();
        ArrayList<String> r = new ArrayList<String>();
        ArrayList<String> p = new ArrayList<String>();
        ArrayList<String> x = new ArrayList<String>();
        for (String vertex : graph.keySet()) {
            p.add(vertex);
        }
        recursiveBronKerbosch(r, p, x);

        ArrayList<String> biggestClique = cliques.get(0);
        for (ArrayList<String> set : cliques) {
            if (set.size() > biggestClique.size()) {
                biggestClique = set;
            }
        }

        String result = "";
        while (!biggestClique.isEmpty()) {
            String current = biggestClique.get(0);
            for (String e : biggestClique) {
                if (e.compareTo(current) < 0) {
                    current = e;
                }
            }
            biggestClique.remove(current);
            result += current + ",";
        }
        result = result.substring(0, result.length() - 1);
        System.out.println(result);
    }

    private static void recursiveBronKerbosch(
        ArrayList<String> r,
        ArrayList<String> p,
        ArrayList<String> x
    ) {
        if (p.isEmpty()) {
            if (x.isEmpty()) {
                cliques.add(r);
            }
            return;
        }

        while (!p.isEmpty()) {
            String vertex = p.get(0);
            ArrayList<String> newR = new ArrayList<String>();
            for (String e : r) {
                newR.add(e);
            }
            newR.add(vertex);
            ArrayList<String> newP = new ArrayList<String>();
            for (String e : p) {
                if (graph.get(vertex).contains(e)) {
                    newP.add(e);
                }
            }
            ArrayList<String> newX = new ArrayList<String>();
            for (String e : x) {
                if (graph.get(vertex).contains(e)) {
                    newX.add(e);
                }
            }

            recursiveBronKerbosch(newR, newP, newX);
            p.remove(vertex);
            x.add(vertex);
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
