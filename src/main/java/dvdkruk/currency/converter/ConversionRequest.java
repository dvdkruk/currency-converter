package dvdkruk.currency.converter;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Data holder and helper for conversion requests.
 */
public class ConversionRequest {

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
     * Parses the given args into a {@code ConversionRequest} object;
     *
     * @param args given input args
     * @return a {@code ConversionRequest} object
     */
    public static ConversionRequest parse(String[] args) {
        BigDecimal amount = null;
        String fromCurrency = null;
        String toCurrency = null;

        if (args.length == 4) {
            if (args[2].equals(IN_KEYWORD)) {
                fromCurrency = args[0];
                toCurrency = args[3];
                try {
                    amount = new BigDecimal(args[1]);
                } catch (NumberFormatException nfe) {
                    //invalid request
                }
            }
        }

        return new ConversionRequest(amount, fromCurrency, toCurrency);
    }

    private boolean validCurrencyCode(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
            return true;
        } catch (NullPointerException | IllegalArgumentException npe) {
            // null or invalid currency code
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
