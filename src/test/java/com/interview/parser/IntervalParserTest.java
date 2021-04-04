package com.interview.parser;

import com.interview.Utils;
import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;
import static com.interview.parser.FieldParser.FieldType.DAY_OF_MONTH;
import static org.junit.Assert.*;

/**
 *
 * @author Ming Li
 */
public class IntervalParserTest {
    private final IntervalParser intervalParser = new IntervalParser();
    
    @Test
    public void testMatch() {
        Assert.assertTrue(intervalParser.match("/", MINUTE));
        Assert.assertTrue(intervalParser.match("1/", HOUR));
        Assert.assertTrue(intervalParser.match("1/k", DAY_OF_MONTH));
        Assert.assertTrue(intervalParser.match("/999", MONTH));
        Assert.assertTrue(intervalParser.match("1/5", DAY_OF_WEEK));

        Assert.assertFalse(intervalParser.match("1-6", COMMAND));
        Assert.assertFalse(intervalParser.match("1,6", COMMAND));
        Assert.assertFalse(intervalParser.match("*", HOUR));
        Assert.assertFalse(intervalParser.match("?", DAY_OF_MONTH));
    }

    @Test
    public void testParse() {

        Assert.assertArrayEquals(Utils.range(0, 59), intervalParser.parse("*/1", MINUTE));
        Assert.assertArrayEquals(Utils.range(0, 23), intervalParser.parse("*/1", HOUR));
        Assert.assertArrayEquals(Utils.range(1, 31), intervalParser.parse("*/1", DAY_OF_MONTH));
        Assert.assertArrayEquals(Utils.range(1, 12), intervalParser.parse("*/1", MONTH));
        Assert.assertArrayEquals(Utils.range(1, 7), intervalParser.parse("*/1", DAY_OF_WEEK));

        intervalParser.parse("0/1", MINUTE);
        intervalParser.parse("0/1", HOUR);
        intervalParser.parse("1/1", DAY_OF_MONTH);
        intervalParser.parse("1/1", MONTH);
        intervalParser.parse("1/1", DAY_OF_WEEK);

        intervalParser.parse("3/5", MINUTE);
        intervalParser.parse("3/5", HOUR);
        intervalParser.parse("3/5", DAY_OF_MONTH);
        intervalParser.parse("3/5", MONTH);
        intervalParser.parse("3/5", DAY_OF_WEEK);
    }
}