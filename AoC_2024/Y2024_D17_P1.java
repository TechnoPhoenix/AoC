import java.io.BufferedReader;
import java.io.FileReader;

public class Y2024_D17_P1 {

    static long registerA;
    static long registerB;
    static long registerC;
    static int ip;

    public static void main(String[] args) {
        //read input file into a string
        String input = inputToString("/home/user/AoC_Inputs/2024/17");

        //extract registers and program from input
        String[] registers = input.split("\n\n")[0].split("\n");
        String programString = input.split("\n\n")[1];
        String[] programArr = programString
            .substring(9, programString.length())
            .trim()
            .split(",");
        int[] program = new int[programArr.length];
        for (int i = 0; i < programArr.length; i++) {
            program[i] = Integer.parseInt(programArr[i]);
        }

        registerA = Integer.parseInt(
            registers[0].substring(12, registers[0].length())
        );
        registerB = Integer.parseInt(
            registers[1].substring(12, registers[1].length())
        );
        registerC = Integer.parseInt(
            registers[2].substring(12, registers[2].length())
        );

        ip = 0;

        String output = "";
        //execute program
        while (ip < program.length) {
            int comboOperand = program[ip + 1];
            int literalOperand = program[ip + 1];
            switch (program[ip]) {
                case 0:
                    //division registerA/(2^comboOperand) written into register A
                    switch (comboOperand) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            //combo operand as literal value
                            registerA = (long) (registerA /
                                (Math.pow(2d, (double) comboOperand)));
                            break;
                        case 4:
                            //combo operand as register A
                            registerA = (long) (registerA /
                                (Math.pow(2d, (double) registerA)));
                            break;
                        case 5:
                            //combo operand as register B
                            registerA = (long) (registerA /
                                (Math.pow(2d, (double) registerB)));
                            break;
                        case 6:
                            //combo operand as register C
                            registerA = (long) (registerA /
                                (Math.pow(2d, (double) registerC)));
                            break;
                        case 7:
                            System.out.println("Invalid combo operand");
                    }
                    ip += 2;
                    break;
                case 1:
                    //bitwise XOR registerB^literalOperand written into register B
                    registerB = registerB ^ literalOperand;
                    ip += 2;
                    break;
                case 2:
                    //modulo comboOperand%8 written into register B
                    switch (comboOperand) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            //combo operand as literal value
                            registerB = comboOperand % 8;
                            break;
                        case 4:
                            //combo operand as register A
                            registerB = registerA % 8;
                            break;
                        case 5:
                            //combo operand as register B
                            registerB = registerB % 8;
                            break;
                        case 6:
                            //combo operand as register C
                            registerB = registerC % 8;
                            break;
                        case 7:
                            System.out.println("Invalid combo operand");
                    }
                    ip += 2;
                    break;
                case 3:
                    //jumpNotZero if register A isnt zero, set ip to literal operand
                    if (registerA == 0) {
                        ip += 2;
                        break;
                    }
                    ip = literalOperand;
                    break;
                case 4:
                    //bitwise XOR registerB^registerC written into register B, reads operand but ignores it
                    registerB = registerB ^ registerC;
                    ip += 2;
                    break;
                case 5:
                    //outputs comboOperand % 8
                    if (!output.equals("")) {
                        output += ",";
                    }
                    switch (comboOperand) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            //combo operand as literal value
                            output += ("" + (comboOperand % 8));
                            break;
                        case 4:
                            //combo operand as register A
                            output += ("" + (registerA % 8));
                            break;
                        case 5:
                            //combo operand as register B
                            output += ("" + (registerB % 8));
                            break;
                        case 6:
                            //combo operand as register C
                            output += ("" + (registerC % 8));
                            break;
                        case 7:
                            System.out.println("Invalid combo operand");
                    }
                    ip += 2;
                    break;
                case 6:
                    //division registerA/(2^comboOperand) written into register B
                    switch (comboOperand) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            //combo operand as literal value
                            registerB = (long) (registerA /
                                (Math.pow(2d, (double) comboOperand)));
                            break;
                        case 4:
                            //combo operand as register A
                            registerB = (long) (registerA /
                                (Math.pow(2d, (double) registerA)));
                            break;
                        case 5:
                            //combo operand as register B
                            registerB = (long) (registerA /
                                (Math.pow(2d, (double) registerB)));
                            break;
                        case 6:
                            //combo operand as register C
                            registerB = (long) (registerA /
                                (Math.pow(2d, (double) registerC)));
                            break;
                        case 7:
                            System.out.println("Invalid combo operand");
                    }
                    ip += 2;
                    break;
                case 7:
                    //division registerA/(2^comboOperand) written into register C
                    switch (comboOperand) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            //combo operand as literal value
                            registerC = (long) (registerA /
                                (Math.pow(2d, (double) comboOperand)));
                            break;
                        case 4:
                            //combo operand as register A
                            registerC = (long) (registerA /
                                (Math.pow(2d, (double) registerA)));
                            break;
                        case 5:
                            //combo operand as register B
                            registerC = (long) (registerA /
                                (Math.pow(2d, (double) registerB)));
                            break;
                        case 6:
                            //combo operand as register C
                            registerC = (long) (registerA /
                                (Math.pow(2d, (double) registerC)));
                            break;
                        case 7:
                            System.out.println("Invalid combo operand");
                    }
                    ip += 2;
                    break;
                default:
                    System.out.println("Invalid opcode");
            }
        }

        //output
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
