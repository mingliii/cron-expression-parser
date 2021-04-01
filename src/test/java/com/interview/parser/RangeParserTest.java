package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Assert;
import org.junit.Test;

import static com.interview.Utils.range;
import static com.interview.parser.FieldParser.FieldType.*;

/**
 * Created on 02/04/2021
 *
 * @author Ming Li
 */
public class RangeParserTest {

    private final RangeParser rangeParser = new RangeParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(rangeParser.match("-", MINUTE));
        Assert.assertTrue(rangeParser.match("1-", HOUR));
        Assert.assertTrue(rangeParser.match("1-k", DAY_OF_MONTH));
        Assert.assertTrue(rangeParser.match("-999", MONTH));
        Assert.assertTrue(rangeParser.match("1-5", DAY_OF_WEEK));

        Assert.assertFalse(rangeParser.match("1/6", COMMAND));
        Assert.assertFalse(rangeParser.match("1,6", COMMAND));
        Assert.assertFalse(rangeParser.match("*", HOUR));
        Assert.assertFalse(rangeParser.match("?", DAY_OF_MONTH));
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_InvalidCharacter() {
        rangeParser.doParse("5-a", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_MissingOnePart() {
        rangeParser.doParse("5-", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_MissingTwoParts() {
        rangeParser.doParse("-", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_MissingTooManyParts() {
        rangeParser.doParse("1-5-10", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_ExceedRange() {
        rangeParser.doParse("0-60", MINUTE);
    }

    @Test
    public void testParseSuccess() {
        Assert.assertArrayEquals(range(0, 59), rangeParser.doParse("0-59", MINUTE));
        Assert.assertArrayEquals(range(10, 45), rangeParser.doParse("10-45", MINUTE));
        Assert.assertArrayEquals(new String[]{"58", "59", "0", "1", "2"}, rangeParser.doParse("58-2", MINUTE));
        Assert.assertArrayEquals(new String[]{"58", "59", "0"}, rangeParser.doParse("58-0", MINUTE));

        Assert.assertArrayEquals(range(0, 23), rangeParser.doParse("0-23", HOUR));
        Assert.assertArrayEquals(range(10, 15), rangeParser.doParse("10-15", HOUR));
        Assert.assertArrayEquals(new String[]{"22", "23", "0", "1"}, rangeParser.doParse("22-1", HOUR));
        Assert.assertArrayEquals(new String[]{"22", "23", "0"}, rangeParser.doParse("22-0", HOUR));

        Assert.assertArrayEquals(range(1, 31), rangeParser.doParse("1-31", DAY_OF_MONTH));
        Assert.assertArrayEquals(range(10, 15), rangeParser.doParse("10-15", DAY_OF_MONTH));
        Assert.assertArrayEquals(new String[]{"30", "31", "1", "2"}, rangeParser.doParse("30-2", DAY_OF_MONTH));


        Assert.assertArrayEquals(range(1, 12), rangeParser.doParse("1-12", MONTH));
        Assert.assertArrayEquals(range(5, 10), rangeParser.doParse("5-10", MONTH));
        Assert.assertArrayEquals(new String[]{"11", "12", "1", "2"}, rangeParser.doParse("11-2", MONTH));

        Assert.assertArrayEquals(range(1, 7), rangeParser.doParse("1-7", DAY_OF_WEEK));
        Assert.assertArrayEquals(range(3, 6), rangeParser.doParse("3-6", DAY_OF_WEEK));
        Assert.assertArrayEquals(new String[]{"5", "6", "7", "1"}, rangeParser.doParse("5-1", DAY_OF_WEEK));

    }
}
