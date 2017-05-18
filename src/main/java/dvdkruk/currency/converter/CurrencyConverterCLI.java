package dvdkruk.currency.converter;


import java.text.DecimalFormat;

public class CurrencyConverterCLI {

    /**
     * Output message when the input is invalid.
     */
    public static final String INVALID_INPUT_FORMAT_MESSAGE = "Invalid input format, expected format is \"<fromCurrency> <amount> in <toCurrency>\" for example \"AUD 101.25 in USD\"";

    /**
     * Ouput template when rate is not available.
     */
    public static final String UNABLE_TO_FIND_RATE_MESSAGE_TEMPLATE = "Unable to find rate for %s/%s";

    /**
     * Message shown when no console arguments are given.
     */
    public static final String STARTUP_MESSAGE = "Currency Converter\n" +
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
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        if (args.length != 0) {
            System.out.println(convCli.process(args));
        } else {
            boolean exit = false;
            System.out.println(STARTUP_MESSAGE);
            do {
                String inputArgs = System.console().readLine().trim();
                if (inputArgs.length() < 1) {
                    System.out.println(STARTUP_MESSAGE);
                } else if (inputArgs.equals("exit")) {
                    exit = true;
                } else {
                    System.out.println(convCli.process(inputArgs.split(" ")));
                }
            } while (!exit);
        }

    }

    /**
     * Processing the given arguments and returns a {@code String} message with the result.
     *
     * @param args input arguments
     * @return a {@code String} message with the result
     */
    public String process(String[] args) {
        ConversionRequest request = ConversionRequest.parse(args);

        if (request.isValid()) {
            ConversionResult result = converter.calculate(request);
            if (result.isValid()) {
                DecimalFormat dfFromCurr = request.getFromCurrency().equals("JPY") ? jpyFormat : defaultFormat;
                DecimalFormat dfToCurr = request.getToCurrency().equals("JPY") ? jpyFormat : defaultFormat;
                return String.format("%s %s = %s %s", request.getFromCurrency(), dfFromCurr.format(request.getAmount()), request.getToCurrency(), dfToCurr.format(result.getAmount()));
            } else {
                return String.format(UNABLE_TO_FIND_RATE_MESSAGE_TEMPLATE, request.getFromCurrency(), request.getToCurrency());
            }
        }
        return INVALID_INPUT_FORMAT_MESSAGE;
    }
}
