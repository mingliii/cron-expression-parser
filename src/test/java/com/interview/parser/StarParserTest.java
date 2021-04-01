package com.interview.parser;

import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;

/**
 * @author Ming Li
 */
public class StarParserTest {

    private final StarParser starParser = new StarParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(starParser.match("*", MINUTE));
        Assert.assertTrue(starParser.match("*", HOUR));
        Assert.assertTrue(starParser.match("*", DAY_OF_MONTH));
        Assert.assertTrue(starParser.match("*", MONTH));
        Assert.assertTrue(starParser.match("*", DAY_OF_WEEK));

        Assert.assertFalse(starParser.match("*", COMMAND));
        Assert.assertFalse(starParser.match("**", COMMAND));
        Assert.assertFalse(starParser.match("5", COMMAND));
        Assert.assertFalse(starParser.match("L", COMMAND));
        Assert.assertFalse(starParser.match(",", MINUTE));
        Assert.assertFalse(starParser.match("-", MINUTE));
        Assert.assertFalse(starParser.match("/", MINUTE));
        Assert.assertFalse(starParser.match("?", MINUTE));
    }

    @Test
    public void testParse() {
        String[] expected;

        expected = new String[60];
        for (int i = 0; i < 60; i++) {
            expected[i] = String.valueOf(i);
        }
        String[] minutes = starParser.parse("*", MINUTE);
        Assert.assertArrayEquals(expected, minutes);

        expected = new String[24];
        for (int i = 0; i < 24; i++) {
            expected[i] = String.valueOf(i);
        }
        String[] hours = starParser.parse("*", HOUR);
        Assert.assertArrayEquals(expected, hours);

        expected = new String[31];
        for (int i = 0; i < 31; i++) {
            expected[i] = String.valueOf(i + 1);
        }
        String[] daysOfMonth = starParser.parse("*", DAY_OF_MONTH);
        Assert.assertArrayEquals(expected, daysOfMonth);

        expected = new String[12];
        for (int i = 0; i < 12; i++) {
            expected[i] = String.valueOf(i + 1);
        }
        String[] months = starParser.parse("*", MONTH);
        Assert.assertArrayEquals(expected, months);

        expected = new String[7];
        for (int i = 0; i < 7; i++) {
            expected[i] = String.valueOf(i + 1);
        }
        String[] daysOfWeek = starParser.parse("*", DAY_OF_WEEK);
        Assert.assertArrayEquals(expected, daysOfWeek);
    }
}
