package dvdkruk.currency.converter;

import dvdkruk.currency.converter.cli.CurrencyConverterCLI;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple CurrencyConverterCLI.
 */
public class CurrencyConverterCLITest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CurrencyConverterCLITest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CurrencyConverterCLITest.class);
    }

    public void testEmptyInput() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("Invalid message format, expected format is \"<fromCurrency> <amount> in <toCurrency>\" for example \"AUD 101.25 in USD\"", cli.process(new String[]{}));
    }

    public void testWrongInput() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("Invalid message format, expected format is \"<fromCurrency> <amount> in <toCurrency>\" for example \"AUD 101.25 in USD\"", cli.process(new String[]{"EURO", "TEST", "in", "AUD"}));
    }

    public void testAudToUsd() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("AUD 100.00 = USD 83.71", cli.process(new String[]{"AUD", "100.00", "in", "USD"}));
    }

    public void testAudToAud() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("AUD 100.00 = AUD 100.00", cli.process(new String[]{"AUD", "100.00", "in", "AUD"}));
    }

    public void testAudToDkk() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("AUD 100.00 = DKK 505.76", cli.process(new String[]{"AUD", "100.00", "in", "DKK"}));
    }

    public void testJpyToUsd() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("JPY 100 = USD 0.83", cli.process(new String[]{"JPY", "100", "in", "USD"}));
    }

    public void testCannotFindRate() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("Unable to find rate for KRW/FJD", cli.process(new String[]{"KRW", "1000.00", "in", "FJD"}));
    }

    public void testLowerCaseCurr() {
        CurrencyConverterCLI cli = new CurrencyConverterCLI();
        assertEquals("Invalid message format, expected format is \"<fromCurrency> <amount> in <toCurrency>\" for example \"AUD 101.25 in USD\"", cli.process(new String[]{"krw", "1000.00", "in", "FJD"}));
    }
}
