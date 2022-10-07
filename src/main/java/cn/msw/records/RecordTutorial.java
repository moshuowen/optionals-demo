package cn.msw.records;

/**
 * @author moshu
 */
public class RecordTutorial {

    public static void main(String[] args) {
        EmployeeClass employeeClass = new EmployeeClass("employeeClass", "123");
        System.out.println(employeeClass);

        EmployeeRecord employeeRecord = new EmployeeRecord("employeeRecord", 321);
        System.out.println(employeeRecord.nameInUpperCase());

        EmployeeRecord.printWhatever();
        new Thread(employeeRecord).start();

    }
}
