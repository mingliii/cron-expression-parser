package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;

/**
 * @author Ming Li
 */
public class ListParserTest {

    private final ListParser listParser = new ListParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(listParser.match("1,5,10", MINUTE));
        Assert.assertTrue(listParser.match("a,b,c", HOUR));
        Assert.assertTrue(listParser.match(",", DAY_OF_WEEK));

        Assert.assertFalse(listParser.match("a,b,c", COMMAND));
        Assert.assertFalse(listParser.match("5", HOUR));
        Assert.assertFalse(listParser.match("c", MONTH));
    }

    @Test
    public void doParse() {

        String[] expected;

        expected = new String[]{"1", "5", "10"};
        Assert.assertArrayEquals(expected, listParser.doParse("1,  5,10", MINUTE));

        expected = new String[]{"0", "5", "23"};
        Assert.assertArrayEquals(expected, listParser.doParse("0,5,23", HOUR));

        expected = new String[]{"1", "5", "31"};
        Assert.assertArrayEquals(expected, listParser.doParse("1, 5, 31", DAY_OF_MONTH));

        expected = new String[]{"1", "5", "12"};
        Assert.assertArrayEquals(expected, listParser.doParse("1, 5, 12", MONTH));

        expected = new String[]{"1", "5", "7"};
        Assert.assertArrayEquals(expected, listParser.doParse("1, 5, 7", DAY_OF_WEEK));
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_ExceedRange() {
        listParser.doParse("0, 5, 31", DAY_OF_MONTH);
    }
}
