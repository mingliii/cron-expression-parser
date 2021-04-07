# 1. Introduction

This project produces a cron express parser that can parse basic cron expression into five fields that describe individual 
details of schedule, plus one command field. All allowed values and special characters for the five fields are described 
in the table below.

| Field  |  Allowed Values | Allowed Special Character |
|---|---|---|
| Minutes  | 0-59  | , - * / |
|  Hours |  0-59 | , - * / |
|  Day of month | 0-23  | , - * ? / L W  |
|  Month | 1-12| , - * /  |
|  Day of week | 1-7|  , - * ?     / L # |

# 2. Build
The project is written by Java and has been pre-built. Its build is managed by Maven. In order to generate runnable 
program - the parser, in the root of the project, run `./mvnw clean install` which runs all unit tests and produces 
`cron-expression-parser.jar` in `scripts` directory.

# 3. Run
`parse.sh` in `scripts` directory is a helper script that passes all command line parameters to`cron-expression-parser.jar`.
Running `./parse.sh "*/15 0 1,15 * 1-5 /usr/bin/find"` produces the output below.

    minute         0 15 30 45
    hour           0
    day of month   1 15
    month          1 2 3 4 5 6 7 8 9 10 11 12
    day of week    1 2 3 4 5
    command        /usr/bin/find

