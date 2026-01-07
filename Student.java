 public class Student extends Person {
    public Student(String name, String id) {
        super(name, id);
    }

    @Override
    public String displayInfo() {
        return "Student -> Name: " + name + ", ID: " + id;
    }
}