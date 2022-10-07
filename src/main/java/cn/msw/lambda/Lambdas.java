package cn.msw.lambda;


import java.sql.Timestamp;
import java.util.Date;

/**
 * @author msw
 */
public class Lambdas {

    public static void main(String[] args) {
        Cat cat = new Cat("mimi");
        Printable lambdaPrintable = (p, s) -> p + ",Meow," + s;
        String s = printThing(lambdaPrintable, cat.getName());
        System.out.println(s);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(new Date(timestamp.getTime()));
    }

    static String printThing(Printable printable, String suffix) {
        return printable.print("prefix", suffix);
    }
}
