import java.math.BigInteger;
public enum OperatorEnum {
    OPERATOR_ADDITION, OPERATOR_SUBTRACTION,
    OPERATOR_MULTIPLICATION, OPERATOR_DIVISION,
    OPERATOR_MODULO,
    OPERATOR_UNKNOWN;
    public static final String ERROR_UNKNOWN_OPERATOR = "Error! Unexpected operator!";
    public BigInteger process(BigInteger lhs, BigInteger rhs) {
        switch (this) {
            case OPERATOR_ADDITION:
                return lhs.add(rhs);
            case OPERATOR_SUBTRACTION:
                return lhs.subtract(rhs);
            case OPERATOR_MULTIPLICATION:
                return lhs.multiply(rhs);
            case OPERATOR_DIVISION:
                return lhs.divide(rhs);
            case OPERATOR_MODULO:
                return lhs.mod(rhs);
        }
        throw new
                RuntimeException(ERROR_UNKNOWN_OPERATOR);
    }
}