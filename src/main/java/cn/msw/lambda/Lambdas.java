package cn.msw.lambda;

/**
 * @author msw
 */
public class Lambdas {

    public static void main(String[] args) {
        Cat cat = new Cat("mimi");
        Printable lambdaPrintable = (p, s) -> p + ",Meow," + s;
        String s = printThing(lambdaPrintable, cat.getName());
        System.out.println(s);

    }

    static String printThing(Printable printable, String suffix) {
        return printable.print("prefix", suffix);
    }
}
