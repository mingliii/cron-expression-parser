package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Test;

import java.util.List;

import static com.interview.parser.FieldParser.FieldType.*;
import static org.junit.Assert.*;

/**
 *
 * @author Ming Li
 */
public class DefaultParserTest {

    private final DefaultParser defaultParser = new DefaultParser();

    @Test
    public void testMatch() {
        assertTrue(defaultParser.match("5", MINUTE));
        assertTrue(defaultParser.match("a", HOUR));

        assertFalse(defaultParser.match("/usr/local/run", COMMAND));
        assertFalse(defaultParser.match("1-5", DAY_OF_MONTH));
        assertFalse(defaultParser.match("1/5", MINUTE));
        assertFalse(defaultParser.match("1,5", MINUTE));
        assertFalse(defaultParser.match("*", MINUTE));
        assertFalse(defaultParser.match("?", MINUTE));
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid() {
        defaultParser.parse("a", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_ExceedRange_Minute() {
        defaultParser.parse("60", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_ExceedRange_Hour() {
        defaultParser.parse("13", MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_BelowRange_Day_Of_Month() {
        defaultParser.parse("0", DAY_OF_MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_BelowRange_Month() {
        defaultParser.parse("0", MONTH);
    }

    @Test
    public void testParseSuccess() {
        List<String> expected = List.of("7");

        assertEquals(expected, defaultParser.parse("7", MINUTE));
        assertEquals(expected, defaultParser.parse("7", HOUR));
        assertEquals(expected, defaultParser.parse("7", DAY_OF_MONTH));
        assertEquals(expected, defaultParser.parse("7", MONTH));
        assertEquals(expected, defaultParser.parse("7", DAY_OF_WEEK));
    }
}
