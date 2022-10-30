package cn.msw.rebase;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        String name=  null;
        Optional.ofNullable(name).orElseThrow(() -> new IllegalArgumentException("name is null"));
        System.out.println(name);
    }

}
