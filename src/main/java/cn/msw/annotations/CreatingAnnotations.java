package cn.msw.annotations;

import java.lang.reflect.Field;

/**
 * @author moshu
 */
public class CreatingAnnotations {

    public static void main(String[] args) {

        @SuppressWarnings("unused")
        Cat myCat = new Cat("Stella");

        if (myCat.getClass().isAnnotationPresent(VeryImportant.class)) {
            System.out.println("this thing is very important");
        }

        for (var method : myCat.getClass().getMethods()) {
            if (method.isAnnotationPresent(RunImmediately.class)) {
                try {
                    int times = method.getAnnotation(RunImmediately.class).times();
                    for (int i = 0; i < times; i++) {
                        method.invoke(myCat);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Field[] declaredFields = myCat.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(ImportantString.class)) {
                try {
                    field.setAccessible(true);
                    Object objectValue = field.get(myCat);
                    if (objectValue instanceof String stringValue) {
                        field.set(myCat, stringValue.toUpperCase());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(myCat.getName());
    }
}
