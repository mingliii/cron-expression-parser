package com.interview;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CronExpressionTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testInvalid_TooManyFields() {
        expectedEx.expect(NotvalidCronExpressionException.class);
        expectedEx.expectMessage("Invalid expression '*/45 0 1,2,15 * 1-5' - reason: The number of fields should be 6, given: 5");

        CronExpression cronExpression = new CronExpression("*/45 0 1,2,15 * 1-5");
    }

    @Test
    public void testInvalid_TooFewManyFields() {
        expectedEx.expect(NotvalidCronExpressionException.class);
        expectedEx.expectMessage("Invalid expression '*/45 0 1,2,15 * 1-5 ? /usr/bin/find' - reason: The number of fields should be 6, given: 7");

        CronExpression cronExpression = new CronExpression("*/45 0 1,2,15 * 1-5 ? /usr/bin/find");
    }


}