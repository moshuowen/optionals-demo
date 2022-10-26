package cn.msw.functional;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        define();

    }

    public static void define() {

        Function<String, Integer> function1 = (String s) -> s.length();
        System.out.println(function1.apply("hello world"));

        Function<String, Integer> function2 = (String s) -> {
            return s.length();
        };
        System.out.println(function1.apply("hello world"));
    }
}
