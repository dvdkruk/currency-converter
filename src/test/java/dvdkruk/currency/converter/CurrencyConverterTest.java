package dvdkruk.currency.converter;

import junit.framework.TestCase;

import java.math.BigDecimal;

/**
 * Created by damia on 18/05/2017.
 */
public class CurrencyConverterTest extends TestCase {

    private CurrencyConverter converter = new CurrencyConverter();

    public void testAudToUsd() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "AUD", "USD"));
        assertEquals(new BigDecimal("0.8371"), result.getAmount());
    }

    public void testCadToUsd() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "CAD", "USD"));
        assertEquals(new BigDecimal("0.8711"), result.getAmount());
    }

    public void testUsdToCny() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "USD", "CNY"));
        assertEquals(new BigDecimal("6.1715"), result.getAmount());
    }

    public void testEurToUsd() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "EUR", "USD"));
        assertEquals(new BigDecimal("1.2315"), result.getAmount());
    }

    public void testGbpToUsd() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "GBP", "USD"));
        assertEquals(new BigDecimal("1.5683"), result.getAmount());
    }

    public void testNzdToUsd() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "NZD", "USD"));
        assertEquals(new BigDecimal("0.7750"), result.getAmount());
    }

    public void testUsdToJpy() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "USD", "JPY"));
        assertEquals(new BigDecimal("119.95"), result.getAmount());
    }

    public void testEurToCzk() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "EUR", "CZK"));
        assertEquals(new BigDecimal("27.6028"), result.getAmount());
    }

    public void testEurToDkk() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "EUR", "DKK"));
        assertEquals(new BigDecimal("7.4405"), result.getAmount());
    }

    public void testEurToNok() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "EUR", "NOK"));
        assertEquals(new BigDecimal("8.6651"), result.getAmount());
    }

    public void testAudToJpy() {
        ConversionResult result = converter.calculate(new ConversionRequest(new BigDecimal("1"), "AUD", "JPY"));
        assertEquals(new BigDecimal("100.410145"), result.getAmount());
    }

    public void testConvert() {
    }

}