package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Test;

import java.util.List;

import static com.interview.Utils.range;
import static com.interview.parser.FieldParser.FieldType.*;
import static java.util.List.of;
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
        assertEquals(range(0, 59), intervalParser.parse("*/ 1", MINUTE));
        assertEquals(range(0, 23), intervalParser.parse("*/1", HOUR));
        assertEquals(range(1, 31), intervalParser.parse("*/1", DAY_OF_MONTH));
        assertEquals(range(1, 12), intervalParser.parse("*/1", MONTH));
        assertEquals(range(1, 7), intervalParser.parse("*/1", DAY_OF_WEEK));

        assertEquals(range(0, 59), intervalParser.parse("0/1", MINUTE));
        assertEquals(range(0, 23), intervalParser.parse("0/1", HOUR));
        assertEquals(range(1, 31), intervalParser.parse("1/1", DAY_OF_MONTH));
        assertEquals(range(1, 12), intervalParser.parse("1/1", MONTH));
        assertEquals(range(1, 7), intervalParser.parse("1/1", DAY_OF_WEEK));

        assertEquals(of("5"), intervalParser.parse("5/0", MINUTE));
        assertEquals(of("5"), intervalParser.parse("5/0", HOUR));
        assertEquals(of("5"), intervalParser.parse("5/0", DAY_OF_MONTH));
        assertEquals(of("5"), intervalParser.parse("5/0", MONTH));
        assertEquals(of("5"), intervalParser.parse("5/0", DAY_OF_WEEK));

        assertEquals(of("5"), intervalParser.parse("5/60", MINUTE));
        assertEquals(of("5"), intervalParser.parse("5/48", HOUR));
        assertEquals(of("5"), intervalParser.parse("5/31", DAY_OF_MONTH));
        assertEquals(of("5"), intervalParser.parse("5/24", MONTH));
        assertEquals(of("5"), intervalParser.parse("5/21", DAY_OF_WEEK));

        List<String> expected;

        expected = List.of("3", "34", "5", "36", "7", "38", "9", "40", "11", "42", "13", "44", "15", "46", "17", "48",
                "19", "50", "21", "52", "23", "54", "25", "56", "27", "58", "29", "0", "31", "2", "33", "4", "35", "6",
                "37", "8", "39", "10", "41", "12", "43", "14", "45", "16", "47", "18", "49", "20", "51", "22", "53", "24",
                "55", "26", "57", "28", "59", "30", "1", "32");
        assertEquals(expected, intervalParser.parse("3/31", MINUTE));

        expected = List.of("3", "16", "5", "18", "7", "20", "9", "22", "11", "0", "13", "2", "15", "4", "17", "6", "19",
                "8", "21", "10", "23", "12", "1", "14");
        assertEquals(expected, intervalParser.parse("3/13", HOUR));

        expected = of("3", "14", "25", "5", "16", "27", "7", "18", "29", "9", "20", "31", "11", "22", "2", "13", "24",
                "4", "15", "26", "6", "17", "28", "8", "19", "30", "10", "21", "1", "12", "23");
        assertEquals(expected, intervalParser.parse("3/11", DAY_OF_MONTH));

        assertEquals(of("3", "10", "5", "12", "7", "2", "9", "4", "11", "6", "1", "8"), intervalParser.parse("3/7", MONTH));
        assertEquals(of("3", "6", "2", "5", "1", "4", "7"), intervalParser.parse("3/3", DAY_OF_WEEK));
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalidFieldFormat() {
        intervalParser.parse("1/", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalidFieldFormat_CharacterNotAcceptable() {
        intervalParser.parse("a/", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartMinute() {
        intervalParser.parse("60/1", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidMinuteInterval() {
        intervalParser.parse("60/a", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartHour() {
        intervalParser.parse("24/1", HOUR);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidHourInterval() {
        intervalParser.parse("24/a", HOUR);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartDayOfMonth() {
        intervalParser.parse("0/1", DAY_OF_MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartDayOfMonth_ExceedDayOfMonth() {
        intervalParser.parse("32/1", DAY_OF_MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidDayOfMonthInterval() {
        intervalParser.parse("1/a", DAY_OF_MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartMonth() {
        intervalParser.parse("0/1", MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartMonth_ExceedMonth() {
        intervalParser.parse("13/1", MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidMonthInterval() {
        intervalParser.parse("1/a", MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidStartDayOfWeek() {
        intervalParser.parse("0/1", DAY_OF_WEEK);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidDayOfWeek_ExceedDayOfWeek() {
        intervalParser.parse("8/1", DAY_OF_WEEK);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidDayOfWeekInterval() {
        intervalParser.parse("1/a", DAY_OF_WEEK);
    }
}