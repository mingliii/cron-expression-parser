package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;

/**
 *
 * @author Ming Li
 */
public class DefaultParserTest {

    private final DefaultParser defaultParser = new DefaultParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(defaultParser.match("5", MINUTE));
        Assert.assertTrue(defaultParser.match("a", HOUR));

        Assert.assertFalse(defaultParser.match("/usr/local/run", COMMAND));
        Assert.assertFalse(defaultParser.match("1-5", DAY_OF_MONTH));
        Assert.assertFalse(defaultParser.match("1/5", MINUTE));
        Assert.assertFalse(defaultParser.match("1,5", MINUTE));
        Assert.assertFalse(defaultParser.match("*", MINUTE));
        Assert.assertFalse(defaultParser.match("?", MINUTE));
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid() {
        defaultParser.doParse("a", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_ExceedRange_Minute() {
        defaultParser.doParse("60", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_ExceedRange_Hour() {
        defaultParser.doParse("13", MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_BelowRange_Day_Of_Month() {
        defaultParser.doParse("0", DAY_OF_MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_BelowRange_Month() {
        defaultParser.doParse("0", MONTH);
    }

    @Test
    public void testParseSuccess() {
        String[] expected = new String[]{"7"};

        Assert.assertArrayEquals(expected, defaultParser.doParse("7", MINUTE));
        Assert.assertArrayEquals(expected, defaultParser.doParse("7", HOUR));
        Assert.assertArrayEquals(expected, defaultParser.doParse("7", DAY_OF_MONTH));
        Assert.assertArrayEquals(expected, defaultParser.doParse("7", MONTH));
        Assert.assertArrayEquals(expected, defaultParser.doParse("7", DAY_OF_WEEK));
    }
}
