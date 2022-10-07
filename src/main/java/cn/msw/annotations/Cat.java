package cn.msw.annotations;


@VeryImportant
public class Cat {

    @ImportantString
    private final String name;


    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @RunImmediately(times = 3)
    public void meow() {
        System.out.println("meow!");
    }

    public void eat() {
        System.out.println("munch!");
    }
}
