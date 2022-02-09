import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)  {

        ArrayList<String> hexes = new ArrayList<>();
        File inputFile = new File("input.txt");
        File outputFile = new File("output.txt");


        try  {
            Scanner sc = new Scanner(inputFile);
            sc.useDelimiter("[, \\s]+");

            while (sc.hasNext()) {
                String opCode, dest, src1, src2;
                String binaries = "";
                int imm, address;

                String instruction = sc.next();
                opCode = Instruction.valueOf(instruction.toUpperCase()).getOpCode();

                // Okudugumuz veriyi opcodeuna gore gruplandiriyourzzz
                switch (instruction.toUpperCase()) {
                    case "ADD":
                    case "AND":
                    case "OR":
                    case "XOR":
                        dest = sc.next();
                        src1 = sc.next();
                        src2 = sc.next();

                        binaries = opCode + regToBinary(dest) + regToBinary(src1) + regToBinary(src2) + "00";
                        break;
                    case "ADDI":
                    case "ANDI":
                    case "ORI":
                    case "XORI":
                        dest = sc.next();
                        src1 = sc.next();
                        imm = sc.nextInt();

                        binaries = opCode + regToBinary(dest) + regToBinary(src1) + toBinary(imm, 6);

                        break;
                    case "JUMP":
                        address = sc.nextInt();

                        binaries = opCode + toBinary(address, 14);
                        break;
                    case "LD":
                        dest = sc.next();
                        address = sc.nextInt();

                        binaries = opCode + regToBinary(dest) + toBinary(address, 10);
                        break;
                    case "ST":
                        src1 = sc.next();
                        address = sc.nextInt();

                        binaries = opCode + regToBinary(src1) + toBinary(address, 10);
                        break;


                    case "BEQ":
                        src1 = sc.next();
                        src2 = sc.next();
                        address = sc.nextInt();

                        binaries = opCode + regToBinary(src1) + regToBinary(src2) + "010" +  toBinary(address, 3);

                        break;
                    case "BGT":
                        src1 = sc.next();
                        src2 = sc.next();
                        address = sc.nextInt();

                        binaries = opCode + regToBinary(src1) + regToBinary(src2) + "001" +  toBinary(address, 3);

                        break;
                    case "BLT":
                        src1 = sc.next();
                        src2 = sc.next();
                        address = sc.nextInt();

                        binaries = opCode + regToBinary(src1) + regToBinary(src2) + "100" +  toBinary(address, 3);

                        break;
                    case "BGE":
                        src1 = sc.next();
                        src2 = sc.next();
                        address = sc.nextInt();

                        binaries = opCode + regToBinary(src1) + regToBinary(src2) + "011" +  toBinary(address, 3);

                        break;
                    case "BLE":
                        src1 = sc.next();
                        src2 = sc.next();
                        address = sc.nextInt();


                        binaries = opCode + regToBinary(src1) + regToBinary(src2) + "110" +  toBinary(address, 3);

                        break;
                    default:
                        System.out.println("WRONG INSTRUCTION!!!!!!!!!!");
                        System.exit(-1);
                }

                System.out.println(binaries);

                int[] results = new int[binaries.length()];
                for (int i = 0; i < binaries.length(); i++) {
                    results[i] = Integer.parseInt(String.valueOf(binaries.charAt(i)));

                }
                hexes.add(getHexaDecimal(results));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try ( FileWriter writer = new FileWriter(outputFile)) {
            writer.write("v2.0 raw\n");
            for (int i = 0; i < hexes.size(); i++) {
                writer.write(hexes.get(i));
                if (i != hexes.size() - 1) {
                    writer.write("\n");
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String regToBinary(String register) {
        int value = Integer.parseInt(register.substring(1));

        return (toBinary(value, 4));
    }

    public static String toBinary(int value, int padding) {

        String result = Integer.toBinaryString(value);

        if (value < 0) {
            return result.substring(32-padding);
        }


        result = String.format("%" + padding + "s", result).replace(' ', '0');

        return result;
    }

    static char makeHex(int number) {
        char returned = ' ';
        if (number >= 0 && number <= 9) {
            returned =  (char) ('0' + number);
        }
        else if (number <= 15) {
            returned =  (char) ('A' + (number - 10));
        }
        return returned;
    }

    /**Returns hexadecimal String from
     * given binary array
     * */
    static String getHexaDecimal (int[] binaryArray) {
        String hexaDecimal = "";
        int exponent = 0;
        int value = 0;
        for (int i = binaryArray.length - 1; i >= 0; i--) {
            if (exponent % 4 == 0 && exponent != 0) {
                hexaDecimal += makeHex(value);
                exponent = value = 0;
            }
            value += binaryArray[i] * (int) (Math.pow(2, exponent++));
        }
        hexaDecimal += makeHex(value);

        StringBuilder returnString = new StringBuilder();
        returnString.append(hexaDecimal);
        returnString.reverse(); // We should use reverse order

        return returnString.toString();
    }
}
