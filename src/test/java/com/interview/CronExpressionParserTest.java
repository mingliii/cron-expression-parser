package com.interview;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ming Li
 */
public class CronExpressionParserTest {

    private  final CronExpressionParser cronExpressionParser = new CronExpressionParser();

    @Test
    public void testParse_WithNewLine() {
        CronExpressionResult results = cronExpressionParser.parse("* * * * * \n\n /usr/bin/find");
        assertEquals(
                "minute        0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59\n" +
                        "hour          0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23\n" +
                        "day of month  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\n" +
                        "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week   1 2 3 4 5 6 7\n" +
                        "command       /usr/bin/find", results.toString());
    }


    @Test
    public void testParse_WithNewLineInCommand() {
        CronExpressionResult results = cronExpressionParser.parse("* * * * * /usr/bin/\n\nfind");
        assertEquals(
                "minute        0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59\n" +
                        "hour          0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23\n" +
                        "day of month  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\n" +
                        "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week   1 2 3 4 5 6 7\n" +
                        "command       /usr/bin/find", results.toString());
    }

    @Test
    public void testParse_AllStars() {
        CronExpressionResult results = cronExpressionParser.parse("* * * * * /usr/bin/find");
        assertEquals(
                "minute        0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59\n" +
                "hour          0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23\n" +
                "day of month  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   1 2 3 4 5 6 7\n" +
                "command       /usr/bin/find", results.toString());
    }

    @Test
    public void testParse_WithQuestionMark() {
        CronExpressionResult results = cronExpressionParser.parse("0/15 0 1,15 * ? /usr/bin/find");
        assertEquals(
                "minute        0 15 30 45\n" +
                "hour          0\n" +
                "day of month  1 15\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   \n" +
                "command       /usr/bin/find", results.toString());
    }

    @Test
    public void testParse() {
        CronExpressionResult results = cronExpressionParser.parse("0/15 0 1,15 * 1-5 /usr/bin/find");
        assertEquals(
                 "minute        0 15 30 45\n" +
                 "hour          0\n" +
                 "day of month  1 15\n" +
                 "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                 "day of week   1 2 3 4 5\n" +
                 "command       /usr/bin/find", results.toString());
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testParseInvalid_TooFewFields() {
        cronExpressionParser.parse("0/15 0 1,15 * 1-5");
    }

//    @Test(expected = NotValidCronExpressionException.class)
//    public void testParseInvalid_TooManyFields() {
//        cronExpressionParser.parse("0/15 0 1,15 * 1-5 * /usr/bin/find");
//    }
}