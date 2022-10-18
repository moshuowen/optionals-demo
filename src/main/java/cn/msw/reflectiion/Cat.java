package cn.msw.reflectiion;

/**
 * @author moshu
 */
@SuppressWarnings("unused")
public class Cat {

    private final String name;

    private int age;


    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
