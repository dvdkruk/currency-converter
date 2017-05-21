package dvdkruk.currency.converter.messages;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class ConversionRequestTest extends TestCase {

    private static final String amountString = "100.12";

    private static final String fromCurrency = "AUD";

    private static final String toCurrency = "EUR";

    private static final ConversionRequest request = new ConversionRequest(new BigDecimal(amountString), fromCurrency, toCurrency);

    private static final ConversionRequest request1 = new ConversionRequest(new BigDecimal(amountString), fromCurrency, toCurrency);

    public void testAllNullArguments() {
        ConversionRequest request = new ConversionRequest(null, null, null);
        assertFalse(request.isValid());
    }

    public void testAmountNullArguments() {
        ConversionRequest request = new ConversionRequest(null, "AUS", "EUR");
        assertFalse(request.isValid());
    }

    public void testFromCurrencyNullArguments() {
        ConversionRequest request = new ConversionRequest(new BigDecimal("12345.123"), null, "AUS");
        assertFalse(request.isValid());
    }

    public void testFromToCurrencyNullArguments() {
        ConversionRequest request = new ConversionRequest(new BigDecimal("12345.1232323"), "EUR", null);
        assertFalse(request.isValid());
    }

    public void testParseValidStringArr() {
        String amount = "1337.101";
        String fromCurr = "AUD";
        String toCurr = "EUR";
        String[] stringArr = new String[]{fromCurr, amount, "in", toCurr};

        ConversionRequest request = ConversionRequest.parse(stringArr);
        assertTrue(request.isValid());
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
        assertTrue(request.isValid());
        assertEquals(amount, request.getAmount().toString());
        assertEquals(fromCurr, request.getFromCurrency());
        assertEquals(toCurr, request.getToCurrency());
        assertEquals(String.format("%s %s in %s", fromCurr, amount, toCurr), request.toString());
    }

    public void testToCurrencyIsNotValid() {
        ConversionRequest invalidRequest = new ConversionRequest(new BigDecimal(amountString), fromCurrency, "XYZ");
        assertFalse(invalidRequest.isValid());
    }

    public void testFromCurrencyIsNotValid() {
        ConversionRequest invalidRequest = new ConversionRequest(new BigDecimal(amountString), "ABC", toCurrency);
        assertFalse(invalidRequest.isValid());
    }

    public void testIsValid() throws Exception {
        assertTrue(request.isValid());
    }

    public void testToString() throws Exception {
        assertEquals(fromCurrency + " " + amountString + " in " + toCurrency, request.toString());
    }

    public void testHashCode() throws Exception {
        assertEquals(request.hashCode(), request1.hashCode());
    }

    public void testEquals() throws Exception {
        assertTrue(request1.equals(request));
        assertTrue(request.equals(request1));
        assertEquals(request, request1);
    }

    public void testGetAmount() throws Exception {
        assertEquals(new BigDecimal(amountString), request.getAmount());
    }

    public void testGetFromCurrency() throws Exception {
        assertEquals(fromCurrency, request.getFromCurrency());
    }

    public void testGetToCurrency() throws Exception {
        assertEquals(toCurrency, request.getToCurrency());
    }

}