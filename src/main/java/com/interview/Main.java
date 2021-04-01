package com.interview;

public class Main {

    public static void main(String[] args) {
        CronExpressionParser parser = new CronExpressionParser();
        CronExpressionResults results = parser.parse("1-50 0/23 1/23 * , /usr/bin/find");
        System.out.println(results);
    }
}
