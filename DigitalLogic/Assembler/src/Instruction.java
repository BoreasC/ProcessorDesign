public enum Instruction {

    ADD("0000"),
    AND("0001"),
    OR("0010"),
    XOR("0011"),
    ADDI("0100"),
    ANDI("0101"),
    ORI("0110"),
    XORI("0111"),
    JUMP("1000"),
    LD("1001"),
    ST("1010"),
    BEQ("1011"),
    BGT("1100"),
    BLT("1101"),
    BGE("1110"),
    BLE("1111");

    private final String opCode;

    Instruction(String opCode) {
        this.opCode = opCode;
    }

    public String getOpCode() {
        return opCode;
    }

}
