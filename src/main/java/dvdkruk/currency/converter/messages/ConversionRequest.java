package dvdkruk.currency.converter.messages;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data holder and helper for conversion requests.
 */
public class ConversionRequest {

    private static final Logger LOGGER = Logger.getLogger(ConversionRequest.class.getName());

    private static final String IN_KEYWORD = "in";

    private final BigDecimal amount;

    private final String fromCurrency;

    private final String toCurrency;

    private final boolean valid;

    public ConversionRequest(BigDecimal amount, String fromCurrency, String toCurrency) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.valid = amount != null && validCurrencyCode(fromCurrency) && validCurrencyCode(toCurrency);
    }

    /**
     * Parses the [@code args} argument into a {@code ConversionRequest} object;
     *
     * @param args provided messages args
     * @return a {@code ConversionRequest} object
     */
    public static ConversionRequest parse(String[] args) {
        BigDecimal amount = null;
        String fromCurrency = null;
        String toCurrency = null;

        if (args.length == 4 && IN_KEYWORD.equals(args[2])) {
            fromCurrency = args[0];
            toCurrency = args[3];
            try {
                amount = new BigDecimal(args[1]);
            } catch (NumberFormatException e) {
                //invalid request
                LOGGER.log(Level.FINEST, args[1] + " cannot be parsed to a " + BigDecimal.class, e);
            }
        }
        return new ConversionRequest(amount, fromCurrency, toCurrency);
    }

    private boolean validCurrencyCode(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
            return true;
        } catch (NullPointerException | IllegalArgumentException e) {
            // null or invalid currency code
            LOGGER.log(Level.FINEST, currencyCode + " is not a supported ISO 4217 currency code", e);
        }
        return false;
    }

    /**
     * @return {@code true} when this is a valid request.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @return a {@code String} representing the request.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s", this.fromCurrency, this.amount.toString(), IN_KEYWORD, this.toCurrency);
    }


    /**
     * Request amount for conversion.
     *
     * @return the amount requested for conversion
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Currency code of the request amount.
     *
     * @return the currency code of the request amount.
     */
    public String getFromCurrency() {
        return fromCurrency;
    }

    /**
     * Target currency code for conversion.
     *
     * @return the target currency code.
     */
    public String getToCurrency() {
        return toCurrency;
    }
}
