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
        rangeParser.parse("5-a", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_MissingOnePart() {
        rangeParser.parse("5-", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_MissingTwoParts() {
        rangeParser.parse("-", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_MissingTooManyParts() {
        rangeParser.parse("1-5-10", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParse_ExceedRange() {
        rangeParser.parse("0-60", MINUTE);
    }

    @Test
    public void testParseSuccess() {
        Assert.assertArrayEquals(range(0, 59), rangeParser.parse("0-59", MINUTE));
        Assert.assertArrayEquals(range(10, 45), rangeParser.parse("10-45", MINUTE));
        Assert.assertArrayEquals(new String[]{"58", "59", "0", "1", "2"}, rangeParser.parse("58-2", MINUTE));
        Assert.assertArrayEquals(new String[]{"58", "59", "0"}, rangeParser.parse("58-0", MINUTE));

        Assert.assertArrayEquals(range(0, 23), rangeParser.parse("0-23", HOUR));
        Assert.assertArrayEquals(range(10, 15), rangeParser.parse("10-15", HOUR));
        Assert.assertArrayEquals(new String[]{"22", "23", "0", "1"}, rangeParser.parse("22-1", HOUR));
        Assert.assertArrayEquals(new String[]{"22", "23", "0"}, rangeParser.parse("22-0", HOUR));

        Assert.assertArrayEquals(range(1, 31), rangeParser.parse("1-31", DAY_OF_MONTH));
        Assert.assertArrayEquals(range(10, 15), rangeParser.parse("10-15", DAY_OF_MONTH));
        Assert.assertArrayEquals(new String[]{"30", "31", "1", "2"}, rangeParser.parse("30-2", DAY_OF_MONTH));


        Assert.assertArrayEquals(range(1, 12), rangeParser.parse("1-12", MONTH));
        Assert.assertArrayEquals(range(5, 10), rangeParser.parse("5-10", MONTH));
        Assert.assertArrayEquals(new String[]{"11", "12", "1", "2"}, rangeParser.parse("11-2", MONTH));

        Assert.assertArrayEquals(range(1, 7), rangeParser.parse("1-7", DAY_OF_WEEK));
        Assert.assertArrayEquals(range(3, 6), rangeParser.parse("3-6", DAY_OF_WEEK));
        Assert.assertArrayEquals(new String[]{"5", "6", "7", "1"}, rangeParser.parse("5-1", DAY_OF_WEEK));

    }
}
