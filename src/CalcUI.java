import javax.xml.bind.JAXBException;
import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.StringTokenizer;
public class CalcUI {
    public static final String COMMAND_YES = "Y";
    public static final String COMMAND_NO = "N";
    public static final String MESSAGE_ABOUT = "Integer calculator";
    public static final String MESSAGE_CONTINUE = "Continue? (Y/N)>";
    public static final String MESSAGE_INITIAL_STATE = "Initial state:";
    public static final String MESSAGE_INPUT_ERROR = "Input error. More then one token found.";
    public static final String MESSAGE_INPUT_OPERAND = "Input an operand>";
    public static final String MESSAGE_INPUT_OPERATOR = "Input an operation (+/-/*/div/mod)>";
    public static final String MESSAGE_RESULT = "Result:";
    public static final String MESSAGE_TRY_AGAIN = "Try again>";
    public static final String MESSAGE_XML_ERROR = "XML binding error! XML state file may be corrupted.";
    public static final String MESSAGE_IO_ERROR = "Unexpected Input/Output error!";
    public static final String ERROR_ARITHMETIC_PATTERN = "Arithmetic Error: %s";
    public static final String ERROR_TOKEN_PARSE_PATTERN = "Error! Unexpected token: %s";
    private IntCalcApplication application;
    private BufferedReader input;
    private PrintWriter output;
    private StringTokenizer tokenizer;
    private OperatorParser operatorParser;
    public CalcUI(IntCalcApplication application) {
        this.application = application;
        this.input = new BufferedReader(new InputStreamReader(System.in));
        this.output = new PrintWriter(System.out, true);
        this.tokenizer = new StringTokenizer("");
        this.operatorParser = new OperatorParser();
    }
    public void run() {
        output.println(MESSAGE_ABOUT);
        output.print(MESSAGE_INITIAL_STATE);
        output.print(' ');
        try {

            output.println(application.getStateManager().getState());
        } catch (JAXBException e) {
            output.println(MESSAGE_XML_ERROR);
            return;
        }
        boolean cont = true;
        while (cont) {
            OperatorEnum operator;
            BigInteger operand;
            try {
                output.print(MESSAGE_INPUT_OPERATOR);
                output.flush();
                operator = readOperator();
                output.print(MESSAGE_INPUT_OPERAND);
                output.flush();
                operand = readInt();
            } catch (IOException e) {
                output.println(MESSAGE_IO_ERROR);
                output.flush();
                return;
            }
            try {
                BigInteger result =
                        operator.process(application.getStateManager().getState(), operand);
                output.print(MESSAGE_RESULT);
                output.print(' ');
                output.println(result);
                output.flush();

                application.getStateManager().setState(result);
            } catch (JAXBException e) {
                output.println(MESSAGE_XML_ERROR);
                return;
            } catch (ArithmeticException e) {

                output.println(String.format(ERROR_ARITHMETIC_PATTERN, e.getMessage()));
                continue;
            }
            try {
                cont = readContinue();
            } catch (IOException e) {
                output.println(MESSAGE_IO_ERROR);
                return;
            }
        }
    }
    private String readString() throws IOException {
        while (true) {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new
                        StringTokenizer(input.readLine());
            }
            String token = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens()) {
                return token;
            }
            tokenizer = new StringTokenizer("");
            output.println(MESSAGE_INPUT_ERROR);
            output.print(MESSAGE_TRY_AGAIN);
            output.flush();
        }
    }
    private BigInteger readInt() throws IOException {
        while (true) {
            String token = readString();
            try {
                return new BigInteger(token);
            } catch (NumberFormatException e) {

                output.println(String.format(ERROR_TOKEN_PARSE_PATTERN, token));
                output.print(MESSAGE_TRY_AGAIN);
                output.flush();
            }
        }
    }
    private OperatorEnum readOperator() throws
            IOException{
        OperatorEnum operator = null;
        while (operator == null) {
            String token = readString();
            try {
                operator =
                        operatorParser.parseOperator(token);
            } catch (ParseException e) {

                output.println(String.format(ERROR_TOKEN_PARSE_PATTERN, token));
                output.print(MESSAGE_TRY_AGAIN);
                output.flush();
            }
        }
        return operator;
    }
    private boolean readContinue() throws IOException
    {
        output.print(MESSAGE_CONTINUE);
        output.flush();
        while (true) {
            String token = readString();
            if (COMMAND_YES.equalsIgnoreCase(token))
            {
                return true;
            }
            if (COMMAND_NO.equalsIgnoreCase(token)) {
                return false;
            }

            output.println(String.format(ERROR_TOKEN_PARSE_PATTERN, token));
            output.print(MESSAGE_TRY_AGAIN);
            output.flush();
        }
    }
}
