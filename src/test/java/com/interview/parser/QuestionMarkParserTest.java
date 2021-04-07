package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ming Li
 */
public class QuestionMarkParserTest {

    private final QuestionMarkParser questionMarkParser = new QuestionMarkParser();

    @Test
    public void match() {
        assertTrue(questionMarkParser.match("?", DAY_OF_WEEK));
        assertTrue(questionMarkParser.match("?", DAY_OF_MONTH));
        assertTrue(questionMarkParser.match("?", MINUTE));
        assertTrue(questionMarkParser.match("?", HOUR));
        assertTrue(questionMarkParser.match("?", MONTH));
        assertTrue(questionMarkParser.match("?", COMMAND));

        assertFalse(questionMarkParser.match("*", DAY_OF_MONTH));
        assertFalse(questionMarkParser.match("-", DAY_OF_MONTH));
        assertFalse(questionMarkParser.match("/", DAY_OF_MONTH));
        assertFalse(questionMarkParser.match(",", DAY_OF_MONTH));
        assertFalse(questionMarkParser.match("5", DAY_OF_MONTH));
        assertFalse(questionMarkParser.match("a", DAY_OF_MONTH));
    }

    @Test
    public void testParseSuccess() {
        assertTrue(questionMarkParser.parse("?", DAY_OF_WEEK).isEmpty());
        assertTrue(questionMarkParser.parse("?", DAY_OF_MONTH).isEmpty());
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseFail_Minute() {
        questionMarkParser.parse("?", MINUTE);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseFail_Hour() {
        questionMarkParser.parse("?", HOUR);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseFail_Month() {
        questionMarkParser.parse("?", MONTH);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseFail_Command() {
        questionMarkParser.parse("?", COMMAND);
    }
}
