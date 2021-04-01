package com.interview.parser;

import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;

/**
 * @author Ming Li
 */
public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(commandParser.match("any", COMMAND));
        Assert.assertFalse(commandParser.match("any", MINUTE));
        Assert.assertFalse(commandParser.match("any", HOUR));
        Assert.assertFalse(commandParser.match("any", DAY_OF_MONTH));
        Assert.assertFalse(commandParser.match("any", MONTH));
        Assert.assertFalse(commandParser.match("any", DAY_OF_WEEK));
    }

    @Test
    public void testParse() {
        String[] expected = new String[]{"any"};
        String[] actual = commandParser.doParse("any", COMMAND);

        Assert.assertArrayEquals(expected, actual);
    }
}
