package cn.msw.sql;

import static java.util.regex.Pattern.compile;

public class test_n {

    public static void main(String[] args) {

        String str = "\n";
        boolean matches = compile("(\"?\n+\"?)").matcher(str).matches();
        if (matches) {
            System.out.println(matches);
        }

    }
}
