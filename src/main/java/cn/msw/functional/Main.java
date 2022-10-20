package cn.msw.functional;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        define();

    }

    public static void define(){

        Function<String, Integer> function1 = String::length;
        System.out.println(function1.apply("hello world"));

    }
}
