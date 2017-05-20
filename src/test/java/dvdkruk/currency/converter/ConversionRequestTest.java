package dvdkruk.currency.converter;

import dvdkruk.currency.converter.messages.ConversionRequest;
import junit.framework.TestCase;

import java.math.BigDecimal;

public class ConversionRequestTest extends TestCase {

    public void testAllNullArguments() {
        ConversionRequest request = new ConversionRequest(null, null, null);
        assertEquals(false, request.isValid());
    }

    public void testAmountNullArguments() {
        ConversionRequest request = new ConversionRequest(null, "AUS", "EUR");
        assertEquals(false, request.isValid());
    }

    public void testFromCurrencyNullArguments() {
        ConversionRequest request = new ConversionRequest(new BigDecimal("12345.123"), null, "AUS");
        assertEquals(false, request.isValid());
    }

    public void testFromToCurrencyNullArguments() {
        ConversionRequest request = new ConversionRequest(new BigDecimal("12345.1232323"), "EUR", null);
        assertEquals(false, request.isValid());
    }

    public void testParseValidStringArr() {
        String amount = "1337.101";
        String fromCurr = "AUD";
        String toCurr = "EUR";
        String[] stringArr = new String[]{fromCurr, amount, "in", toCurr};

        ConversionRequest request = ConversionRequest.parse(stringArr);
        assertEquals(true, request.isValid());
        assertEquals(amount, request.getAmount().toString());
        assertEquals(fromCurr, request.getFromCurrency());
        assertEquals(toCurr, request.getToCurrency());
        assertEquals(String.format("%s %s in %s", fromCurr, amount, toCurr), request.toString());
    }

    public void testValidArguments() {
        String amount = "1337.101";
        String fromCurr = "AUD";
        String toCurr = "EUR";
        ConversionRequest request = new ConversionRequest(new BigDecimal(amount), fromCurr, toCurr);
        assertEquals(true, request.isValid());
        assertEquals(amount, request.getAmount().toString());
        assertEquals(fromCurr, request.getFromCurrency());
        assertEquals(toCurr, request.getToCurrency());
        assertEquals(String.format("%s %s in %s", fromCurr, amount, toCurr), request.toString());
    }

}