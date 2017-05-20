package dvdkruk.currency.converter;

import dvdkruk.currency.converter.messages.ConversionRequest;
import dvdkruk.currency.converter.messages.ConversionResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {

    /**
     * Contains the direct and inverted rates.
     */
    private final Map<String, BigDecimal> conversionTable = new HashMap<>();

    /**
     * Contains the cross conversion rates.
     */
    private final Map<String, String> crossConversionTable = new HashMap<>();

    public CurrencyConverter() {
        // add direct and inverted conversion rates
        addConversionRate("AUD", "USD", new BigDecimal("0.8371"));
        addConversionRate("CAD", "USD", new BigDecimal("0.8711"));
        addConversionRate("USD", "CNY", new BigDecimal("6.1715"));
        addConversionRate("EUR", "USD", new BigDecimal("1.2315"));
        addConversionRate("GBP", "USD", new BigDecimal("1.5683"));
        addConversionRate("NZD", "USD", new BigDecimal("0.7750"));
        addConversionRate("USD", "JPY", new BigDecimal("119.95"));
        addConversionRate("EUR", "CZK", new BigDecimal("27.6028"));
        addConversionRate("EUR", "DKK", new BigDecimal("7.4405"));
        addConversionRate("EUR", "NOK", new BigDecimal("8.6651"));

        // add cross conversion entries
        addCrossConversion("CAD", "USD", "AUD");
        addCrossConversion("CNY", "USD", "AUD");
        addCrossConversion("CZK", "USD", "AUD");
        addCrossConversion("DKK", "USD", "AUD");
        addCrossConversion("EUR", "USD", "AUD");
        addCrossConversion("GBP", "USD", "AUD");
        addCrossConversion("JPY", "USD", "AUD");
        addCrossConversion("NOK", "USD", "AUD");
        addCrossConversion("NZD", "USD", "AUD");
        addCrossConversion("CNY", "USD", "CAD");
        addCrossConversion("CZK", "USD", "CAD");
        addCrossConversion("DKK", "USD", "CAD");
        addCrossConversion("EUR", "USD", "CAD");
        addCrossConversion("GBP", "USD", "CAD");
        addCrossConversion("JPY", "USD", "CAD");
        addCrossConversion("NOK", "USD", "CAD");
        addCrossConversion("NZD", "USD", "CAD");
        addCrossConversion("CZK", "USD", "CNY");
        addCrossConversion("DDK", "USD", "CNY");
        addCrossConversion("EUR", "USD", "CNY");
        addCrossConversion("GBP", "USD", "CNY");
        addCrossConversion("JPY", "USD", "CNY");
        addCrossConversion("NOK", "USD", "CNY");
        addCrossConversion("NZD", "USD", "CNY");
        addCrossConversion("DDK", "EUR", "CZK");
        addCrossConversion("GBP", "USD", "CZK");
        addCrossConversion("JPY", "USD", "CZK");
        addCrossConversion("NOK", "EUR", "CZK");
        addCrossConversion("NZD", "USD", "CZK");
        addCrossConversion("USD", "EUR", "CZK");
        addCrossConversion("GBP", "USD", "DKK");
        addCrossConversion("JPY", "USD", "DKK");
        addCrossConversion("NOK", "EUR", "DKK");
        addCrossConversion("NZD", "USD", "DKK");
        addCrossConversion("USD", "EUR", "DKK");
        addCrossConversion("GBP", "USD", "EUR");
        addCrossConversion("JPY", "USD", "EUR");
        addCrossConversion("NZD", "USD", "EUR");
        addCrossConversion("JPY", "USD", "GBP");
        addCrossConversion("NOK", "USD", "GBP");
        addCrossConversion("NZD", "USD", "GBP");
        addCrossConversion("NOK", "USD", "JPY");
        addCrossConversion("NZD", "USD", "JPY");
        addCrossConversion("NZD", "USD", "NOK");
        addCrossConversion("USD", "EUR", "NOK");
    }

    /**
     * Returns a {@code ConversionResult} for the given request.
     *
     * @param request A request containing the details for currency conversion.
     * @return A {@code ConversionResult} for the given request.
     */
    public ConversionResult calculate(ConversionRequest request) {
        // if from and to currencies are the same; return the amount a result (1:1)
        if (request.getToCurrency().equals(request.getFromCurrency())) {
            return new ConversionResult(request.getAmount());
        }

        BigDecimal result = convert(request.getFromCurrency(), request.getToCurrency(), request.getAmount());
        return new ConversionResult(result);
    }

    /**
     * Returns converted amount in given toCurrency from given amount in given fromCurrency,
     * or {@code null} no conversion between the currencies are available.
     *
     * @param fromCurrency for conversion
     * @param toCurrency   the target currency for conversion
     * @param amount       of from currency to convert
     * @return converted amount in given toCurrency from given amount in given fromCurrency,
     * or {@code null} no conversion between the currencies are available.
     */
    private BigDecimal convert(String fromCurrency, String toCurrency, BigDecimal amount) {

        // look up direct and inverted rates
        String conversionKey = fromCurrency + toCurrency;
        BigDecimal directRate = this.conversionTable.get(conversionKey);
        if (directRate != null) {
            return directRate.multiply(amount);
        }

        // recursively look up cross rates
        String crossVia = this.crossConversionTable.get(fromCurrency + toCurrency);
        if (crossVia != null) {
            BigDecimal crossRate = this.conversionTable.get(fromCurrency + crossVia);
            return convert(crossVia, toCurrency, crossRate.multiply(amount));
        }

        // no conversion rate available
        return null;
    }

    private void addCrossConversion(String fromCurr, String viaCurr, String toCurr) {
        this.crossConversionTable.put(fromCurr + toCurr, viaCurr);
        // reflectional/mirrored entry
        this.crossConversionTable.put(toCurr + fromCurr, viaCurr);
    }

    private void addConversionRate(String fromCurr, String toCurr, BigDecimal rate) {
        this.conversionTable.put(fromCurr + toCurr, rate);
        //add inverted rate
        this.conversionTable.put(toCurr + fromCurr, new BigDecimal("1").divide(rate, 99, RoundingMode.HALF_EVEN));
    }

}
