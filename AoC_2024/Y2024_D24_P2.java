import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

/*
 * is supposed to be a full adder with 2 XOR, 2 AND, 1 OR
 * like here: https://upload.wikimedia.org/wikipedia/commons/6/69/Full-adder_logic_diagram.svg
 * for check each gate check if the wires/operators match what is expected, based on the operators/wires
 * if a gate does not match the structure, add the output wire to the wrong wires
 */

public class Y2024_D24_P2 {

    private record Gate(
        String input1,
        String input2,
        String operator,
        String output
    ) {}

    static HashSet<Gate> gates;
    static ArrayList<String> wrongWires;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/24");

        gates = new HashSet<Gate>();

        String gatesString = input.split("\n\n")[1].trim();
        for(String gateString : gatesString.split("\n")) {
        	String inputs = gateString.split("->")[0].trim();
        	String output = gateString.split("->")[1].trim();
         	String input1 = inputs.split(" ")[0].trim();
          	String operator = inputs.split(" ")[1].trim();
        	String input2 = inputs.split(" ")[2].trim();

        	Gate gate = new Gate(input1, input2, operator, output);

        	gates.add(gate);
        }

        wrongWires = new ArrayList<String>();

        for(Gate gate : gates) {
        	//special case of first carry
        	if((gate.input1.matches("(x|y)00") && gate.input2.matches("(x|y)00")) && gate.operator.equals("AND")) {
        		continue;
        	}
         	//possible violations of full adder structure
        	if(gate.output.charAt(0) == 'z' &&  !gate.output.equals("z45")) {
        		if(!gate.operator.equals("XOR")) {
          			wrongWires.add(gate.output);
        		}
        	} else if(gate.output.charAt(0) != 'z' && (gate.input1.matches("(x|y)[0-9][0-9]") && gate.input2.matches("(x|y)[0-9][0-9]"))) {
       			if(gate.operator.equals("AND")) {
          			boolean foundMatchingGate = false;
             		for(Gate gateInner : gates) {
               			if((gateInner.input1.equals(gate.output) || gateInner.input2.equals(gate.output)) && gateInner.operator.equals("OR")) {
                  			foundMatchingGate = true;
                		}
               		}
          			if(!foundMatchingGate) {
            			wrongWires.add(gate.output);
            		}
        		} else if(gate.operator.equals("XOR")) {
          			boolean foundMatchingGate = false;
           			for(Gate gateInner : gates) {
             			if((gateInner.input1.equals(gate.output) || gateInner.input2.equals(gate.output)) && gateInner.operator.equals("XOR")) {
                			foundMatchingGate = true;
              			}
             		}
          			if(!foundMatchingGate) {
            			wrongWires.add(gate.output);
            		}
        		}
        	} else if(gate.output.charAt(0) != 'z' && !(gate.input1.matches("(x|y)[0-9][0-9]") && gate.input2.matches("(x|y)[0-9][0-9]"))) {
        		if(gate.operator.equals("XOR")) {
          			wrongWires.add(gate.output);
        		}
        	}
        }

        Collections.sort(wrongWires);
        String output = "";
        for(String s : wrongWires) {
        	if(output.equals("")) {
        		output += s;
        	} else {
         		output += "," + s;
        	}
        }
        System.out.println(output);
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
