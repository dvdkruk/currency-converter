package dvdkruk.currency.converter;

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
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals(CurrencyConverterCLI.INVALID_INPUT_FORMAT_MESSAGE, convCli.process(new String[]{}));
    }

    public void testWrongInput() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals(CurrencyConverterCLI.INVALID_INPUT_FORMAT_MESSAGE, convCli.process(new String[]{"EURO", "TEST", "in", "AUD"}));
    }

    public void testAudToUsd() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals("AUD 100.00 = USD 83.71", convCli.process(new String[]{"AUD", "100.00", "in", "USD"}));
    }

    public void testAudToAud() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals("AUD 100.00 = AUD 100.00", convCli.process(new String[]{"AUD", "100.00", "in", "AUD"}));
    }

    public void testAudToDkk() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals("AUD 100.00 = DKK 505.76", convCli.process(new String[]{"AUD", "100.00", "in", "DKK"}));
    }

    public void testJpyToUsd() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals("JPY 100 = USD 0.83", convCli.process(new String[]{"JPY", "100", "in", "USD"}));
    }

    public void testConnotFindRate() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals("Unable to find rate for KRW/FJD", convCli.process(new String[]{"KRW", "1000.00", "in", "FJD"}));
    }

    public void testLowerCaseCurr() {
        CurrencyConverterCLI convCli = new CurrencyConverterCLI();
        assertEquals(CurrencyConverterCLI.INVALID_INPUT_FORMAT_MESSAGE, convCli.process(new String[]{"krw", "1000.00", "in", "FJD"}));
    }
}
