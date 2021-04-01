package com.interview.parser;

import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;

/**
 * Created on 01/04/2021
 *
 * @author Ming Li
 */
public class DefaultParserTest {

    private final DefaultParser defaultParser = new DefaultParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(defaultParser.match("5", MINUTE));
        Assert.assertTrue(defaultParser.match("a", HOUR));
        Assert.assertTrue(defaultParser.match("-", DAY_OF_MONTH));
        Assert.assertTrue(defaultParser.match("5/9", MONTH));
        Assert.assertTrue(defaultParser.match("1-31", DAY_OF_WEEK));

        Assert.assertFalse(defaultParser.match("/usr/local/run", COMMAND));
    }

    @Test
    public void testParse() {
    }
}
