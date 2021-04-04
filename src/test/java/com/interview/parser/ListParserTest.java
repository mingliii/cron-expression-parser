package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Test;

import java.util.List;

import static com.interview.parser.FieldParser.FieldType.*;
import static org.junit.Assert.*;

/**
 * @author Ming Li
 */
public class ListParserTest {

    private final ListParser listParser = new ListParser();

    @Test
    public void testMatch() {
        assertTrue(listParser.match("1,5,10", MINUTE));
        assertTrue(listParser.match("a,b,c", HOUR));
        assertTrue(listParser.match(",", DAY_OF_WEEK));

        assertFalse(listParser.match("a,b,c", COMMAND));
        assertFalse(listParser.match("5", HOUR));
        assertFalse(listParser.match("c", MONTH));
    }

    @Test
    public void parse() {

        List<String> expected;

        expected = List.of("1", "5", "10");
        assertEquals(expected, listParser.parse("1,  5,10", MINUTE));

        expected = List.of("0", "5", "23");
        assertEquals(expected, listParser.parse("0,5,23", HOUR));

        expected = List.of("1", "5", "31");
        assertEquals(expected, listParser.parse("1, 5, 31", DAY_OF_MONTH));

        expected = List.of("1", "5", "12");
        assertEquals(expected, listParser.parse("1, 5, 12", MONTH));

        expected = List.of("1", "5", "7");
        assertEquals(expected, listParser.parse("1, 5, 7", DAY_OF_WEEK));
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_ExceedRange() {
        listParser.parse("0, 5, 31", DAY_OF_MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_InvalidCharacter() {
        listParser.parse("0, 5, a", DAY_OF_MONTH);
    }
}
