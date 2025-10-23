import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Y2024_D21_P2 {

    static char[][] numPad = {
        { '7', '4', '1', '#' },
        { '8', '5', '2', '0' },
        { '9', '6', '3', 'A' },
    };
    static char[][] dirPad = { { '#', '<' }, { '^', 'v' }, { 'A', '>' } };

    private record Pair(char previous, char next) {}

    static HashMap<Pair, HashSet<String>> numericalMap;
    static HashMap<Pair, HashSet<String>> directionalMap;

    static HashMap<Integer, HashMap<Pair, Long>> cache;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/21");

        //split input into codes
        String[] codes = input.split("\n");
        int[] numericPart = new int[codes.length];
        for (int i = 0; i < numericPart.length; i++) {
            numericPart[i] = Integer.parseInt(
                codes[i].substring(0, codes[i].length() - 1)
            );
        }

        //prepare hashmaps for keypads
        numericalMap = new HashMap<Pair, HashSet<String>>();
        String numericalPadOptions = "A0123456789";
        for (int i = 0; i < numericalPadOptions.length(); i++) {
            int xPos = -1;
            int yPos = -1;
            for (int j = 0; j < numPad.length; j++) {
                for (int k = 0; k < numPad[0].length; k++) {
                    if (numericalPadOptions.charAt(i) == numPad[j][k]) {
                        xPos = j;
                        yPos = k;
                    }
                }
            }
            for (int j = 0; j < numericalPadOptions.length(); j++) {
                boolean[][] preVisited =
                    new boolean[numPad.length][numPad[0].length];
                numericalMap.put(
                    new Pair(
                        numericalPadOptions.charAt(i),
                        numericalPadOptions.charAt(j)
                    ),
                    new HashSet<String>()
                );
                recursiveNumpad(
                    numericalPadOptions.charAt(i),
                    numericalPadOptions.charAt(j),
                    xPos,
                    yPos,
                    "",
                    preVisited
                );
            }
        }
        directionalMap = new HashMap<Pair, HashSet<String>>();
        String directionalPadOptions = "<>^vA";
        for (int i = 0; i < directionalPadOptions.length(); i++) {
            int xPos = -1;
            int yPos = -1;
            for (int j = 0; j < dirPad.length; j++) {
                for (int k = 0; k < dirPad[0].length; k++) {
                    if (directionalPadOptions.charAt(i) == dirPad[j][k]) {
                        xPos = j;
                        yPos = k;
                    }
                }
            }
            for (int j = 0; j < directionalPadOptions.length(); j++) {
                boolean[][] preVisited =
                    new boolean[dirPad.length][dirPad[0].length];
                directionalMap.put(
                    new Pair(
                        directionalPadOptions.charAt(i),
                        directionalPadOptions.charAt(j)
                    ),
                    new HashSet<String>()
                );
                recursiveDirpad(
                    directionalPadOptions.charAt(i),
                    directionalPadOptions.charAt(j),
                    xPos,
                    yPos,
                    "",
                    preVisited
                );
            }
        }

        cache = new HashMap<Integer, HashMap<Pair, Long>>();

        long complexitySum = 0;
        for (int i = 0; i < codes.length; i++) {
            long sequenceLength = 0;
            char previous = 'A';
            for (int j = 0; j < codes[i].length(); j++) {
                long shortest = recursiveLength(
                    previous,
                    codes[i].charAt(j),
                    0
                );
                previous = codes[i].charAt(j);
                sequenceLength += shortest;
            }
            complexitySum += numericPart[i] * sequenceLength;
        }

        //output
        System.out.println(
            "The total complexity of all codes is: " + complexitySum
        );
    }

    private static long recursiveLength(char previous, char next, int depth) {
        if (
            cache
                .getOrDefault(depth, new HashMap<Pair, Long>())
                .containsKey(new Pair(previous, next))
        ) {
            return cache.get(depth).get(new Pair(previous, next));
        } else {
            long shortest = Long.MAX_VALUE;
            if (depth == 0) {
                for (String path : numericalMap.get(new Pair(previous, next))) {
                    long length = 0;
                    char prev = 'A';
                    for (int i = 0; i < path.length(); i++) {
                        length += recursiveLength(
                            prev,
                            path.charAt(i),
                            depth + 1
                        );
                        prev = path.charAt(i);
                    }
                    if (length < shortest) {
                        shortest = length;
                    }
                }
            } else if (depth < 26 && depth > 0) {
                for (String path : directionalMap.get(
                    new Pair(previous, next)
                )) {
                    long length = 0;
                    char prev = 'A';
                    for (int i = 0; i < path.length(); i++) {
                        length += recursiveLength(
                            prev,
                            path.charAt(i),
                            depth + 1
                        );
                        prev = path.charAt(i);
                    }
                    if (length < shortest) {
                        shortest = length;
                    }
                }
            } else if (depth == 26) {
                return 1;
            }
            HashMap<Pair, Long> depthCache = cache.getOrDefault(
                depth,
                new HashMap<Pair, Long>()
            );
            depthCache.put(new Pair(previous, next), shortest);
            cache.put(depth, depthCache);
            return shortest;
        }
    }

    private static void recursiveNumpad(
        char start,
        char end,
        int xPos,
        int yPos,
        String path,
        boolean[][] oldPreVisited
    ) {
        if (path.length() > 8) {
            return;
        }
        try {
            if (numPad[xPos][yPos] == end) {
                path += 'A';
                HashSet<String> set = numericalMap.get(new Pair(start, end));
                int smallest = Integer.MAX_VALUE;
                for (String p : set) {
                    if (p.length() < smallest) {
                        smallest = p.length();
                    }
                }
                if (smallest > path.length()) {
                    set.clear();
                    set.add(path);
                    numericalMap.put(new Pair(start, end), set);
                } else if (smallest == path.length()) {
                    set.add(path);
                    numericalMap.put(new Pair(start, end), set);
                }
                return;
            } else if (numPad[xPos][yPos] == '#') {
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }

        boolean[][] preVisited =
            new boolean[oldPreVisited.length][oldPreVisited[0].length];
        for (int i = 0; i < preVisited.length; i++) {
            for (int j = 0; j < preVisited[0].length; j++) {
                preVisited[i][j] = oldPreVisited[i][j];
            }
        }
        if (preVisited[xPos][yPos]) {
            return;
        }

        preVisited[xPos][yPos] = true;
        recursiveNumpad(start, end, xPos + 1, yPos, path + ">", preVisited);
        recursiveNumpad(start, end, xPos - 1, yPos, path + "<", preVisited);
        recursiveNumpad(start, end, xPos, yPos + 1, path + "v", preVisited);
        recursiveNumpad(start, end, xPos, yPos - 1, path + "^", preVisited);
    }

    private static void recursiveDirpad(
        char start,
        char end,
        int xPos,
        int yPos,
        String path,
        boolean[][] oldPreVisited
    ) {
        if (path.length() > 8) {
            return;
        }
        try {
            if (dirPad[xPos][yPos] == end) {
                path += 'A';
                HashSet<String> set = directionalMap.get(new Pair(start, end));
                int smallest = Integer.MAX_VALUE;
                for (String p : set) {
                    if (p.length() < smallest) {
                        smallest = p.length();
                    }
                }
                if (smallest > path.length()) {
                    set.clear();
                    set.add(path);
                    directionalMap.put(new Pair(start, end), set);
                } else if (smallest == path.length()) {
                    set.add(path);
                    directionalMap.put(new Pair(start, end), set);
                }
            } else if (dirPad[xPos][yPos] == '#') {
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }

        boolean[][] preVisited =
            new boolean[oldPreVisited.length][oldPreVisited[0].length];
        for (int i = 0; i < preVisited.length; i++) {
            for (int j = 0; j < preVisited[0].length; j++) {
                preVisited[i][j] = oldPreVisited[i][j];
            }
        }
        if (preVisited[xPos][yPos]) {
            return;
        }

        preVisited[xPos][yPos] = true;
        recursiveDirpad(start, end, xPos + 1, yPos, path + ">", preVisited);
        recursiveDirpad(start, end, xPos - 1, yPos, path + "<", preVisited);
        recursiveDirpad(start, end, xPos, yPos + 1, path + "v", preVisited);
        recursiveDirpad(start, end, xPos, yPos - 1, path + "^", preVisited);
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
