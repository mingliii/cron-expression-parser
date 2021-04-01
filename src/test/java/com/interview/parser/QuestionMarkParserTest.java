package com.interview.parser;

import com.interview.NotValidCronExpressionException;
import org.junit.Assert;
import org.junit.Test;

import static com.interview.parser.FieldParser.FieldType.*;

/**
 * @author Ming Li
 */
public class QuestionMarkParserTest {

    private final QuestionMarkParser questionMarkParser = new QuestionMarkParser();

    @Test
    public void match() {
        Assert.assertTrue(questionMarkParser.match("?", DAY_OF_WEEK));
        Assert.assertTrue(questionMarkParser.match("?", DAY_OF_MONTH));
        Assert.assertTrue(questionMarkParser.match("?", MINUTE));
        Assert.assertTrue(questionMarkParser.match("?", HOUR));
        Assert.assertTrue(questionMarkParser.match("?", MONTH));
        Assert.assertTrue(questionMarkParser.match("?", COMMAND));

        Assert.assertFalse(questionMarkParser.match("*", DAY_OF_MONTH));
        Assert.assertFalse(questionMarkParser.match("-", DAY_OF_MONTH));
        Assert.assertFalse(questionMarkParser.match("/", DAY_OF_MONTH));
        Assert.assertFalse(questionMarkParser.match(",", DAY_OF_MONTH));
        Assert.assertFalse(questionMarkParser.match("5", DAY_OF_MONTH));
        Assert.assertFalse(questionMarkParser.match("a", DAY_OF_MONTH));
    }

    @Test
    public void testParseSuccess() {
        Assert.assertArrayEquals(new String[]{}, questionMarkParser.parse("?", DAY_OF_WEEK));
        Assert.assertArrayEquals(new String[]{}, questionMarkParser.parse("?", DAY_OF_MONTH));
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
