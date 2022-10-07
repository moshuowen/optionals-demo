package cn.msw.reflectiion;

import java.lang.reflect.Field;

/**
 * @author moshu
 */
public class ReflectionTutorial {

    public static void main(String[] args) {
        Cat cat = new Cat("Stella", 2);
        Field[] catFields = cat.getClass().getDeclaredFields();

        for (Field field : catFields) {
            System.out.println("Field name: " + field.getName());
            System.out.println("Field type: " + field.getType());
            if("name".equals(field.getName())) {
                try {
                    field.setAccessible(true);
                    field.set(cat, "Mia");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(cat.getName());
    }
}
