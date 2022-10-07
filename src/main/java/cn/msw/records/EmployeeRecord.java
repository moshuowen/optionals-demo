package cn.msw.records;

public record EmployeeRecord(String name, int employeeNumber) implements Runnable {

    private static final String DEFAULT_EMPLOYEE_NAME = "moshuowen";

    public EmployeeRecord {
        if (name == null) {
            name = DEFAULT_EMPLOYEE_NAME;
        }
        if (employeeNumber < 0) {
            throw new IllegalArgumentException("employeeNumber must be positive");
        }
        // 紧凑型构造器
    }

    public String nameInUpperCase() {
        return name.toUpperCase();
    }

    public static void printWhatever() {
        System.out.println("whatever");
    }

    @Override
    public void run() {
        System.out.println("run");
    }
}
