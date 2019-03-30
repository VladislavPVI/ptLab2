import javax.xml.bind.JAXBException;
import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.StringTokenizer;
public class CalcUI {

    private IntCalcApplication application;
    private BufferedReader input;
    private PrintWriter output;
    private StringTokenizer tokenizer;
    private OperatorParser operatorParser;
    public CalcUI(IntCalcApplication application) {
        this.application = application;
        this.input = new BufferedReader(new InputStreamReader(System.in));
        this.output = new PrintWriter(System.out);
        this.tokenizer = new StringTokenizer("");
        this.operatorParser = new OperatorParser();
    }
    public void run() {
        output.println("Калькулятор больших чисел");
        output.print("Начальное состояние: ");

        try {
            output.println(application.getStateManager().getState());
        } catch (JAXBException e) {
            output.println("Ошибки в XML файле или он не существует");
            return;
        }
        boolean cont = true;
        while (cont) {
            OperatorEnum operator;
            BigInteger operand;
            try {
                output.print("Введи операцию (+/-/*/div/mod)>");
                output.flush();
                operator = readOperator();
                output.print("Введи операнд>");
                output.flush();
                operand = readBigInt();
            } catch (IOException e) {
                output.println("Ошибка ввода!");
                output.flush();
                return;
            }
            try {
                BigInteger result = operator.process(application.getStateManager().getState(), operand);
                output.print("Результат: ");
                output.println(result);
                output.flush();
                application.getStateManager().setState(result);
            } catch (JAXBException e) {
                output.println("Ошибки в XML файле или он не существует");
                return;
            } catch (ArithmeticException e) {

                output.println(String.format("Арифметическая ошибка: %s", e.getMessage()));
                continue;
            }
            try {
                cont = readContinue();
            } catch (IOException e) {
                output.println("Ошибка ввода!");
                return;
            }
        }
    }
    private String readString() throws IOException {
        while (true) {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(input.readLine());
            }
            String token = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens()) {
                return token;
            }
            tokenizer = new StringTokenizer("");
            output.println("Введено больше одного токена");
            output.println("Неудача! Попробуй снова");
            output.flush();

        }
    }
    private BigInteger readBigInt() throws IOException {
        while (true) {
            String token = readString();
            try {
                return new BigInteger(token);
            } catch (NumberFormatException e) {

                output.println(String.format("Ошибка ввода: %s", token));
                output.println("Неудача! Попробуй снова");
                output.flush();

            }
        }
    }
    private OperatorEnum readOperator() throws IOException{
        OperatorEnum operator = null;
        while (operator == null) {
            String token = readString();
            try {
                operator =
                        operatorParser.parseOperator(token);
            } catch (ParseException e) {

                output.println(String.format("Ошибка ввода: %s", token));
                output.println("Неудача! Попробуй снова");
                output.flush();

            }
        }
        return operator;
    }
    private boolean readContinue() throws IOException
    {
        output.print("Повторить? (Y/N)");
        output.flush();

        while (true) {
            String token = readString();
            if ("Y".equalsIgnoreCase(token))
            {
                return true;
            }
            if ("N".equalsIgnoreCase(token)) {
                return false;
            }

            output.println(String.format("Ошибка ввода: %s", token));
            output.println("Неудача! Попробуй снова");
            output.flush();

        }
    }
}
