package cn.msw;


import java.util.Optional;

/**
 * @author shuowenmo
 */
public class Main {

    public static void main(String[] args) {
        Person person = new Person("John", "JOHN@gmail.com");
        String emailNotProvided = person.getEmail().map(String::toLowerCase).orElse("email not provided");
        System.out.println(emailNotProvided);

        if (person.getEmail().isPresent()) {
            System.out.println(person.getEmail().get().toLowerCase());
        }else if (person.getEmail().isEmpty()) {
            System.out.println("email not provided");
        }

    }
}

class Person {

    private final String name;
    private final String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }
}