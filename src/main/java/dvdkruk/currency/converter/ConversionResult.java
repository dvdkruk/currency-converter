package dvdkruk.currency.converter;

import java.math.BigDecimal;

/**
 * Data holder for a conversion result from a {@see ConversionRequest}.
 */
class ConversionResult {

    private final BigDecimal amount;

    ConversionResult(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return {@code true} when this result is successful conversion result.
     */
    boolean isValid() {
        return amount != null;
    }

    BigDecimal getAmount() {
        return amount;
    }
}
