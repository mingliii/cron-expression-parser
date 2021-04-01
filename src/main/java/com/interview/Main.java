package com.interview;

public class Main {

    public static void main(String[] args) {
        CronExpressionParser parser = new CronExpressionParser();
        CronExpressionResults results = parser.parse("*/15 0/23 1-23 * 3-6 /usr/bin/find");
        System.out.println(results);
    }
}
