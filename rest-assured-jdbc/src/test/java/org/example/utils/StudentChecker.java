package org.example.utils;

import lombok.experimental.UtilityClass;

import org.example.api.ApiRepository;
import org.example.api.students.payload.entity.StudentDto;
import org.junit.jupiter.api.Assertions;

import java.util.NoSuchElementException;

@UtilityClass
public class StudentChecker {

    public void checkStudentNotExists(int studentId) {
        try {
            ApiRepository.studentsApi.getStudent(studentId);
            Assertions.fail("Студент с ID " + studentId + " не должен существовать, но был найден");

        } catch (NoSuchElementException e) {
            System.out.println("Студент ожидаемо не найден: " + e.getMessage());
        }
    }

    public void checkStudentExists(int studentId) {
        StudentDto student = ApiRepository.studentsApi.getStudent(studentId);
        Assertions.assertNotNull(student, "Студент с ID " + studentId + " должен существовать");
        Assertions.assertEquals(studentId, student.getId(), "ID студента должен совпадать");
    }

    public void verifyStudentData(int studentId, String expectedName, int[] expectedMarks) {
        StudentDto student = ApiRepository.studentsApi.getStudent(studentId);
        Assertions.assertEquals(expectedName, student.getName(), "Имя студента должно совпадать");
        Assertions.assertArrayEquals(expectedMarks, student.getMarks(), "Оценки студента должны совпадать");
    }
}
