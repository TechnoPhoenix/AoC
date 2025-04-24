package Day24_2024_12_24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Problem2 {
	
	private record Operation(String operand1, String operand2, String operator) {}

	static HashMap<String, Boolean> wires;
	static HashMap<String, Operation> operations;
	
	public static void main(String[] args) {
		//read input file into a string
		String input = inputToString("src/Day24_2024_12_24/input.txt");

		//turn input into wires and operations
		String wireStates = input.split("\n\n")[0];
		String operationString = input.split("\n\n")[1];
		wires = new HashMap<String, Boolean>();
		operations = new HashMap<String, Operation>();
		for(String wireState : wireStates.split("\n")) {
			if(wireState.charAt(wireState.length() - 1) == '1') {
				wires.put(wireState.split(":")[0], true);
			} else if(wireState.charAt(wireState.length() - 1) == '0') {
				wires.put(wireState.split(":")[0], false);
			} else {
				System.out.println("Invalid wire state");
			}
		}

		for(String operation : operationString.split("\n")) {
			String[] opPart = operation.split("->")[0].trim().split(" ");
			String resPart = operation.split("->")[1].trim();

			operations.put(resPart, new Operation(opPart[0], opPart[2], opPart[1]));
		}

		HashMap<String, Boolean> zWires = new HashMap<String, Boolean>();

		for(Map.Entry<String, Operation> operation : operations.entrySet()) {
			if(operation.getKey().charAt(0) == 'z') {
				zWires.put(operation.getKey(), performOperation(operation.getValue()));
			}
		}

		long decimalNumberX = 0;
		long decimalNumberY = 0;
		for(Map.Entry<String, Boolean> wire : wires.entrySet()) {
			if(wire.getKey().charAt(0) == 'x') {
				if(wire.getValue()) {
					int exp = Integer.parseInt(wire.getKey().substring(1));
					decimalNumberX += Math.pow(2, exp);
				}
			}
			if(wire.getKey().charAt(0) == 'y') {
				if(wire.getValue()) {
					int exp = Integer.parseInt(wire.getKey().substring(1));
					decimalNumberY += Math.pow(2, exp);
				}
			}
		}
		long desiredZ = decimalNumberX + decimalNumberY;


		long decimalNumberZ = 0;
		for(Map.Entry<String, Boolean> wire : zWires.entrySet()) {
			if(wire.getValue()) {
				int exp = Integer.parseInt(wire.getKey().substring(1));
				decimalNumberZ += Math.pow(2, exp);
			}
		}

		String desiredZString = Long.toBinaryString(desiredZ);
		String decimalZString = Long.toBinaryString(decimalNumberZ);
		for(int i = 0; i < decimalZString.length(); i++) {
			if(decimalZString.charAt(i) != desiredZString.charAt(i)) {
				System.out.println("Mismatch at " + i);
			}
		}
	}

	private static boolean performOperation(Operation operation) {
		boolean operand1;
		boolean operand2;
		if(wires.containsKey(operation.operand1)) {
			operand1 = wires.get(operation.operand1);
		} else {
			operand1 = performOperation(operations.get(operation.operand1));
		}
		if(wires.containsKey(operation.operand2)) {
			operand2 = wires.get(operation.operand2);
		} else {
			operand2 = performOperation(operations.get(operation.operand2));
		}
		switch(operation.operator) {
			case "AND":
			return (operand1 && operand2);
			case "OR":
			return (operand1 || operand2);
			case "XOR":
			return (operand1 ^ operand2);
			default:
			System.out.println("Unkown Operator");
			return false;
		}
	}

	private static String inputToString(String filename) {
        String result = "";
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
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