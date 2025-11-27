package org.example;

public class Utils {

    public Student createStudent(String name, Integer... grades) {
        Student student = new Student(name);
        for (Integer grade : grades) {
            student.addGrade(grade);
        }
        return student;
    }
}
