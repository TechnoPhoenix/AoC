import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D05_P2 {

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/5");

        //split into rules and updates
        String orderingRulesString = input.split("\n\n")[0];
        String updatesString = input.split("\n\n")[1];

        //put rules into 2d int array
        String[] orderingRulesSeperated = orderingRulesString.split("\n");
        int[][] orderingRules = new int[orderingRulesSeperated.length][2];
        for (int i = 0; i < orderingRulesSeperated.length; i++) {
            String[] temp = orderingRulesSeperated[i].split("\\|");
            try {
                orderingRules[i][0] = Integer.parseInt(temp[0]);
                orderingRules[i][1] = Integer.parseInt(temp[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //check updates for correct ordering
        String[] updates = updatesString.split("\n");
        boolean[] incorrect = new boolean[updates.length];
        for (int i = 0; i < incorrect.length; i++) {
            incorrect[i] = false;
        }
        for (int i = 0; i < updates.length; i++) {
            String[] updateString = updates[i].split(",");
            int[] update = new int[updateString.length];
            for (int j = 0; j < updateString.length; j++) {
                update[j] = Integer.parseInt(updateString[j]);
            }
            for (int j = 0; j < update.length; j++) {
                for (int k = 0; k < orderingRules.length; k++) {
                    if (update[j] == orderingRules[k][1]) {
                        if (isInArray(update, orderingRules[k][0])) {
                            if (
                                !isRuleFullfilled(
                                    update,
                                    orderingRules[k][0],
                                    j
                                )
                            ) {
                                incorrect[i] = true;
                            }
                        }
                    }
                }
            }
        }

        int sumOfCorrectedUpdates = 0;
        for (int i = 0; i < incorrect.length; i++) {
            if (incorrect[i]) {
                sumOfCorrectedUpdates += reorderCorrectly(
                    updates[i],
                    orderingRules
                );
            }
        }

        //output
        System.out.println(
            "The sum of all middle elements of correct updates is: " +
                sumOfCorrectedUpdates
        );
    }

    private static int reorderCorrectly(String updates, int[][] rules) {
        String[] updateString = updates.split(",");
        int[] update = new int[updateString.length];
        for (int j = 0; j < updateString.length; j++) {
            update[j] = Integer.parseInt(updateString[j]);
        }
        for (int l = 0; l < update.length; l++) {
            for (int i = (update.length - 1); i >= 0; i--) {
                for (int j = 0; j < rules.length; j++) {
                    if (update[i] == rules[j][1]) {
                        if (isInArray(update, rules[j][0])) {
                            if (!isRuleFullfilled(update, rules[j][0], i)) {
                                int[] newOrder = new int[update.length];
                                int pos = 0;
                                for (int k = 0; k < update.length; k++) {
                                    if (update[k] == rules[j][0]) {
                                        pos = k;
                                        break;
                                    }
                                }
                                for (int k = 0; k < i; k++) {
                                    newOrder[k] = update[k];
                                }
                                newOrder[i] = update[pos];
                                newOrder[i + 1] = update[i];
                                for (int k = i + 2; k < newOrder.length; k++) {
                                    if (k <= pos) {
                                        newOrder[k] = update[k - 1];
                                    } else {
                                        newOrder[k] = update[k];
                                    }
                                }
                                update = newOrder;
                            }
                        }
                    }
                }
            }
        }
        return update[update.length / 2];
    }

    private static boolean isInArray(int[] arr, int element) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRuleFullfilled(
        int[] updates,
        int requiredInt,
        int indexOfElement
    ) {
        for (int i = 0; i < indexOfElement; i++) {
            if (requiredInt == updates[i]) {
                return true;
            }
        }
        return false;
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
