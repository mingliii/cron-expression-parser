package com.interview.parser;

import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.MINUTE;

/**
 * @author Ming Li
 */
public class ListParserTest {

    private final ListParser listParser = new ListParser();

    @Test
    public void testMatch() {
        Assert.assertTrue(listParser.match("1,5,10", MINUTE));
        Assert.assertTrue(listParser.match("a,b,c", MINUTE));
    }

    @Test
    public void doParse() {
    }
}
