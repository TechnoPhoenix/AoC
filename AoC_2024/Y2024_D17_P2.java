import java.io.BufferedReader;
import java.io.FileReader;

/*
 * explanation of my thought process:
 *
 * first, examine input to determine the following:
 * -the only jump instruction is at the end and jumps to the start, creating a loop which contains all of the other instructions
 * -the second to last instruction is the only instruction with an output, meaning we have one output per iteration of the afformentioned loop
 *
 * now lets determine what happens each iteration to see what factors influence the value of the output:
 * 	opcode	operand		resulting operation			in other words
 * 	2		4			B = A%8						B is set to the three least significant bits of A
 * 	1		5			B = B XOR 5					flip first and third least significant bit of B
 * 	7		5			C = A/(2^B)					shift A by B bits to the right
 * 	1		6			B = B XOR 6					flip second and third least significant bit of B
 * 	0		3			A = A/(2^3)					shift A three bits to the right
 * 	4		6			B = B XOR C					only read of C
 * 	5		5			output B%8					output three least significant bits of B
 * 	3		0			jumps to zero				starts next iteration
 *
 * we can now see, that C is determined by B and A, also B is determined by A and C
 * furthermore we can see that B and C are not persisent between iterations, so lets try to reduce the output to A
 * output = ((((A%8) XOR 5) XOR 6) XOR (A/(2^((A%8) XOR 5))))%8
 *
 * from the instruction with opcode 0 and operand 3 we also know that we "lose" 3 bits of A every iteration,
 * meaning we start with 3 for the last output, then 6 for the second to last, etc
 *
 * we can now attempt to determine the three bits required for an output, then use them to determine the remaining three for the previous output
 * this was we only have to brute-force 8 possible arrangements for each output
 */

public class Y2024_D17_P2 {

    static long[] bitSegments;
    static int[] program;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/17");

        //extract program from input
        String programString = input.split("\n\n")[1];
        String[] programArr = programString
            .substring(9, programString.length())
            .trim()
            .split(",");
        program = new int[programArr.length];
        for (int i = 0; i < programArr.length; i++) {
            program[i] = Integer.parseInt(programArr[i]);
        }

        bitSegments = new long[program.length];
        for (int i = 0; i < bitSegments.length; i++) {
            bitSegments[i] = -1;
        }

        //attempt to brute-force every output from last to first
        recursiveOutputAttempts(0);

        //connect all segments to create initial value for register A
        long initialRegisterA = 0;
        for (int i = 0; i < bitSegments.length; i++) {
            initialRegisterA = initialRegisterA << 3;
            initialRegisterA += bitSegments[i];
            System.out.println("Segment is " + bitSegments[i]);
        }

        //output
        System.out.println(
            "The initial value for register A is: " + initialRegisterA
        );
    }

    private static boolean recursiveOutputAttempts(int depth) {
        if (depth == bitSegments.length) {
            return true;
        }

        long previousValues = 0;
        for (int i = 0; i < depth; i++) {
            previousValues += bitSegments[i];
            previousValues = previousValues << 3;
        }

        //attempt to determine bits for this output
        for (int i = 0; i < 8; i++) {
            long A = previousValues + i;
            long resultingOutput =
                ((((A % 8) ^ 5) ^ 6) ^
                    (long) (A / (Math.pow(2, ((A % 8) ^ 5))))) %
                8;
            if (resultingOutput == program[program.length - 1 - depth]) {
                bitSegments[depth] = i;
                if (recursiveOutputAttempts(depth + 1)) {
                    return true;
                } else {
                    bitSegments[depth] = -1;
                    continue;
                }
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
