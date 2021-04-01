package com.interview;

public class Main {

    public static void main(String[] args) {
        CronExpressionParser parser = new CronExpressionParser();
        CronExpressionResults results = parser.parse("0 1-2 32-2 * 5 /usr/bin/find");
        System.out.println(results);
    }
}
