package dvdkruk.currency.converter;

import java.math.BigDecimal;

/**
 * Data holder for a conversion result from a {@see ConversionRequest}.
 */
public class ConversionResult {

    private final BigDecimal amount;

    public ConversionResult(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return {@code true} when this result is successful conversion result.
     */
    public boolean isValid() {
        return amount != null;
    }

    /**
     * Returns the result amount of the conversion when the result is valid.
     *
     * @return the result amount of the conversion when the result is valid
     */
    public BigDecimal getAmount() {
        return amount;
    }
}
