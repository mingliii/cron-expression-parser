package com.interview.parser;

import org.junit.Test;

import java.util.List;

import static com.interview.parser.FieldParser.FieldType.*;
import static org.junit.Assert.*;

/**
 * @author Ming Li
 */
public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser();

    @Test
    public void testMatch() {
        assertTrue(commandParser.match("any", COMMAND));
        assertFalse(commandParser.match("any", MINUTE));
        assertFalse(commandParser.match("any", HOUR));
        assertFalse(commandParser.match("any", DAY_OF_MONTH));
        assertFalse(commandParser.match("any", MONTH));
        assertFalse(commandParser.match("any", DAY_OF_WEEK));
    }

    @Test
    public void testParse() {
        List<String> expected = List.of("any");
        List<String> actual = commandParser.parse("any", COMMAND);

        assertEquals(expected, actual);
    }
}
