package com.interview.parser;

import com.interview.Utils;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;
import static org.junit.Assert.*;

/**
 *
 * @author Ming Li
 */
public class IntervalParserTest {
    private final IntervalParser intervalParser = new IntervalParser();
    
    @Test
    public void testMatch() {
        assertTrue(intervalParser.match("/", MINUTE));
        assertTrue(intervalParser.match("1/", HOUR));
        assertTrue(intervalParser.match("1/k", DAY_OF_MONTH));
        assertTrue(intervalParser.match("/999", MONTH));
        assertTrue(intervalParser.match("1/5", DAY_OF_WEEK));

        assertFalse(intervalParser.match("1-6", COMMAND));
        assertFalse(intervalParser.match("1,6", COMMAND));
        assertFalse(intervalParser.match("*", HOUR));
        assertFalse(intervalParser.match("?", DAY_OF_MONTH));
    }

    @Test
    public void testParse() {

        assertEquals(Utils.range(0, 59), intervalParser.parse("*/1", MINUTE));
        assertEquals(Utils.range(0, 23), intervalParser.parse("*/1", HOUR));
        assertEquals(Utils.range(1, 31), intervalParser.parse("*/1", DAY_OF_MONTH));
        assertEquals(Utils.range(1, 12), intervalParser.parse("*/1", MONTH));
        assertEquals(Utils.range(1, 7), intervalParser.parse("*/1", DAY_OF_WEEK));

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