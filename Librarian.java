package edu.oop.library.lab3.model;
public class Librarian {
    private final String employeeId;
    private String name;
    private String department;
    public Librarian(String employeeId, String name, String department) {
        this.employeeId = employeeId; this.name = name; setDepartment(department);
    }
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) {
        if (department == null || department.isBlank()) throw new IllegalArgumentException("Відділ обов'язковий");
        this.department = department.strip();
    }
    public boolean mayProcessLoans() { return !department.equalsIgnoreCase("Архів"); }
    @Override public String toString() { return "Бібліотекар " + name + " / " + department; }
}
