package com.interview;

public class Main {

    public static void main(String[] args) {
        String expression;
        if (args.length == 1) {
            expression =args[0];
            System.out.println("Cron expression: " + expression);
        } else {
            System.out.println("Please provide one cron expression only!");
            expression = "*/15 * 1,15 * MON,SAT /usr/bin/find";
            System.out.println("This is an example of cron express: " + expression);
        }

        CronExpressionParser parser = new CronExpressionParser();
//        CronExpressionResults results = parser.parse("*/15 * 1,15 * 1-5 /usr/bin/find");
        CronExpressionResults results = parser.parse(expression);
        System.out.println("Output:");
        System.out.println(results);
    }
}
