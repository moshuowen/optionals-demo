package cn.msw.records;

import java.util.Objects;

/**
 * @author moshu
 */
@SuppressWarnings("unused")
public class EmployeeClass {

    private final String name;

    private final String employeeNumber;

    public EmployeeClass(String name, String employeeId) {
        this.name = name;
        this.employeeNumber = employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeClass that = (EmployeeClass) o;

        if (!Objects.equals(name, that.name)) {
            return false;
        }
        return Objects.equals(employeeNumber, that.employeeNumber);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (employeeNumber != null ? employeeNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeClass{" +
                "name='" + name + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                '}';
    }
}
