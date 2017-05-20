package dvdkruk.currency.converter.cli;

import dvdkruk.currency.converter.CurrencyConverter;
import dvdkruk.currency.converter.messages.ConversionRequest;
import dvdkruk.currency.converter.messages.ConversionResult;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.logging.Logger;

public class CurrencyConverterCLI {

    private static final Logger LOGGER = Logger.getLogger(CurrencyConverterCLI.class.getName());

    /**
     * Output message when the input is invalid.
     */
    private static final String INVALID_INPUT_FORMAT_MESSAGE = "Invalid message format, expected format is \"<fromCurrency> <amount> in <toCurrency>\" for example \"AUD 101.25 in USD\"";

    /**
     * Output template when rate is not available.
     */
    private static final String UNABLE_TO_FIND_RATE_MESSAGE_TEMPLATE = "Unable to find rate for %s/%s";

    /**
     * Message shown when no console arguments are given.
     */
    private static final String STARTUP_MESSAGE = "Currency Converter\n" +
            "Commands:\n" +
            "- <fromCurrency> <amount> in <toCurrency> -> convert amounts between currencies, for example \"AUD 101.25 in USD\".\n" +
            "- exit -> exit the program";

    /**
     * Default decimal format with 2 decimals. Used for most currencies, except JPY {@code jpyFormat}.
     */
    private final DecimalFormat defaultFormat = new DecimalFormat();
    /**
     * Decimal format for JPY currency with 0 decimals.
     */
    private final DecimalFormat jpyFormat = new DecimalFormat();
    /**
     * The converter used to calculate amount between currencies.
     */
    private final CurrencyConverter converter = new CurrencyConverter();

    public CurrencyConverterCLI() {
        // init default decimal format
        this.defaultFormat.setMaximumFractionDigits(2);
        this.defaultFormat.setMinimumFractionDigits(2);
        this.defaultFormat.setGroupingUsed(false);

        // init decimal format for JPY
        this.jpyFormat.setMaximumFractionDigits(0);
        this.jpyFormat.setMinimumFractionDigits(0);
        this.jpyFormat.setGroupingUsed(false);
    }

    /**
     * Program main.
     *
     * @param args console arguments
     */
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            LOGGER.severe("No console.");
            System.exit(1);
        }
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        if (args.length != 0) {
            console.writer().println(cli.process(args));
        } else {
            boolean exit = false;
            console.writer().println(STARTUP_MESSAGE);
            do {
                String inputArgs = cli.readNextInput();
                if (inputArgs == null) {
                    console.writer().println("Invalid messages");
                } else if ("exit".equals(inputArgs)) {
                    exit = true;
                } else {
                    console.writer().println(cli.process(inputArgs.split(" ")));
                }
            } while (!exit);
        }

    }

    private String readNextInput() {
        String inputArgs = System.console().readLine();
        if (inputArgs != null) {
            String trimmedInput = inputArgs.trim();
            if (trimmedInput.length() != 0) {
                return trimmedInput;
            }
        }
        return null;
    }

    /**
     * Processing the given arguments and returns a {@code String} messages with the result.
     *
     * @param args messages arguments
     * @return a {@code String} messages with the result
     */
    public String process(String[] args) {
        ConversionRequest request = ConversionRequest.parse(args);

        if (request.isValid()) {
            ConversionResult result = converter.calculate(request);
            if (result.isValid()) {
                DecimalFormat dfFromCurr = "JPY".equals(request.getFromCurrency()) ? jpyFormat : defaultFormat;
                DecimalFormat dfToCurr = "JPY".equals(request.getToCurrency()) ? jpyFormat : defaultFormat;
                return String.format("%s %s = %s %s", request.getFromCurrency(), dfFromCurr.format(request.getAmount()), request.getToCurrency(), dfToCurr.format(result.getAmount()));
            } else {
                return String.format(UNABLE_TO_FIND_RATE_MESSAGE_TEMPLATE, request.getFromCurrency(), request.getToCurrency());
            }
        }
        return INVALID_INPUT_FORMAT_MESSAGE;
    }
}
