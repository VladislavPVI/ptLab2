import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
public class OperatorParser {
    private Map<String, OperatorEnum> operatorMap = new HashMap<>();
    public OperatorParser() {
        operatorMap.put("+", OperatorEnum.OPERATOR_ADDITION);
        operatorMap.put("-", OperatorEnum.OPERATOR_SUBTRACTION);
        operatorMap.put("*", OperatorEnum.OPERATOR_MULTIPLICATION);
        operatorMap.put("div", OperatorEnum.OPERATOR_DIVISION);
        operatorMap.put("mod", OperatorEnum.OPERATOR_MODULO);
    }
    public OperatorEnum parseOperator(String token) throws ParseException {
        OperatorEnum operator = operatorMap.get(token);
        if (operator == null) {
            throw new ParseException(String.format("Ошибка ввода: %s", token), 0);
        }
        return operator;
    }
}