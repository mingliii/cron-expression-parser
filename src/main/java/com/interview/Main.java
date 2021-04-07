package com.interview;

public class Main {

    public static void main(String[] args) {
        String expression;
        if (args.length == 1) {
            expression =args[0];
            System.out.println("Cron expression: " + expression);
        } else {
            System.out.println("Please provide one cron expression only!");
            expression = "*/15 * 1,15 * 1-5 /usr/bin/find";
            System.out.println("This is an example of cron express: " + expression);
        }

        try {
            CronExpressionParser parser = new CronExpressionParser();
            CronExpressionResult results = parser.parse(expression);
            System.out.println("Output:");
            System.out.println(results);
        } catch (Exception e) {
            System.out.println("Output:");
            System.err.println(e.getMessage());
        }
    }
}
