package com.interview.parser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.interview.parser.FieldParser.FieldType.*;
import static org.junit.Assert.*;

/**
 * @author Ming Li
 */
public class StarParserTest {

    private final StarParser starParser = new StarParser();

    @Test
    public void testMatch() {
        assertTrue(starParser.match("*", MINUTE));
        assertTrue(starParser.match("*", HOUR));
        assertTrue(starParser.match("*", DAY_OF_MONTH));
        assertTrue(starParser.match("*", MONTH));
        assertTrue(starParser.match("*", DAY_OF_WEEK));

        assertFalse(starParser.match("*", COMMAND));
        assertFalse(starParser.match("**", COMMAND));
        assertFalse(starParser.match("5", COMMAND));
        assertFalse(starParser.match("L", COMMAND));
        assertFalse(starParser.match(",", MINUTE));
        assertFalse(starParser.match("-", MINUTE));
        assertFalse(starParser.match("/", MINUTE));
        assertFalse(starParser.match("?", MINUTE));
    }

    @Test
    public void testParse() {
        List<String> expected;

        expected = new ArrayList<>(60);
        for (int i = 0; i < 60; i++) {
            expected.add(String.valueOf(i));
        }
        List<String> minutes = starParser.parse("*", MINUTE);
        assertEquals(expected, minutes);

        expected = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            expected.add(String.valueOf(i));
        }
        List<String> hours = starParser.parse("*", HOUR);
        assertEquals(expected, hours);

        expected = new ArrayList<>(31);
        for (int i = 0; i < 31; i++) {
            expected.add(String.valueOf(i+1));
        }
        List<String> daysOfMonth = starParser.parse("*", DAY_OF_MONTH);
        assertEquals(expected, daysOfMonth);

        expected = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            expected.add(String.valueOf(i+1));
        }
        List<String> months = starParser.parse("*", MONTH);
        assertEquals(expected, months);

        expected = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            expected.add(String.valueOf(i+1));
        }
        List<String> daysOfWeek = starParser.parse("*", DAY_OF_WEEK);
        assertEquals(expected, daysOfWeek);
    }
}
