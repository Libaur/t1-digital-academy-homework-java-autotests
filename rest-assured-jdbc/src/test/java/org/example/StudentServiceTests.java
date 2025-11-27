package org.example;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.example.api.ApiRepository;
import org.example.api.students.StudentsApi;
import org.example.api.students.payload.entity.StudentDto;
import org.example.utils.StudentDataGenerator;
import org.example.utils.StudentBuilder;
import org.example.utils.StudentChecker;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.NoSuchElementException;

public class StudentServiceTests {

    private final StudentsApi studentsApi = ApiRepository.studentsApi;
    private int studentId;
    private String studentName;
    private int[] marks = new int[]{2, 3, 5};

    @BeforeAll
    static void init() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    void setup() {
        studentId = StudentDataGenerator.generateId();
        studentName = StudentDataGenerator.generateName();
    }

    @AfterEach
    void cleanup() {
        studentsApi.deleteStudent(studentId);
        StudentChecker.checkStudentNotExists(studentId);
    }

    @Test
    @DisplayName("Добавляет студента в базу, если студента с таким ID ранее не было")
    void addStudentWithId() {

        StudentChecker.checkStudentNotExists(studentId);
        int statusCode = studentsApi.addStudent(StudentBuilder.createStudent(studentId, studentName, null));
        Assertions.assertEquals(201, statusCode, "Статус код добавления студента 201");
        StudentChecker.checkStudentExists(studentId);
    }

    @Test
    @DisplayName("Добавляет студента в базу, если ID null, то возвращается назначенный ID")
    void addStudentWithoutId() {

        int addedStudentId = studentsApi.addStudent(StudentBuilder.createStudent(null, studentName, null));
        StudentChecker.checkStudentExists(addedStudentId);
    }

    @Test
    @DisplayName("Сработает валидация, если имя не заполнено")
    void addStudentWithoutName() {

        StudentChecker.checkStudentNotExists(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            studentsApi.addStudent(StudentBuilder.createStudent(studentId, "", null));
        });
        StudentChecker.checkStudentNotExists(studentId);
    }

    @Test
    @DisplayName("Обновляет студента в базе, если студент с таким ID ранее был")
    void updateStudent() {

        int[] marks = new int[]{2, 3, 5};
        String newName = StudentDataGenerator.generateName();
        StudentChecker.checkStudentNotExists(studentId);
        studentsApi.addStudent(StudentBuilder.createStudent(studentId, studentName, null));
        StudentChecker.checkStudentExists(studentId);

        int statusCode = studentsApi.addStudent(StudentBuilder.createStudent(studentId, newName, marks));

        Assertions.assertEquals(201, statusCode, "Статус код обновления студента 201");
        StudentChecker.verifyStudentData(studentId, newName, marks);
    }

    @Test
    @DisplayName("Удаляет студента с указанным ID")
    void deletedStudentFound() {

        StudentChecker.checkStudentNotExists(studentId);
        studentsApi.addStudent(StudentBuilder.createStudent(studentId, studentName, null));
        int statusCode = studentsApi.deleteStudent(studentId);

        Assertions.assertEquals(200, statusCode, "Статус код удаления студента 200");
        StudentChecker.checkStudentNotExists(studentId);

    }

    @Test
    @DisplayName("Удаляемого студента с таким ID в базе нет")
    void deletedStudentNotFound() {

        StudentChecker.checkStudentNotExists(studentId);
        int statusCode = studentsApi.deleteStudent(studentId);
        Assertions.assertEquals(404, statusCode, "Статус код не нахождения студента при удалении 404");

    }

    @Test
    @DisplayName("Возвращает студента с указанным ID и заполненным именем")
    void studentFound() {

        StudentChecker.checkStudentNotExists(studentId);
        studentsApi.addStudent(StudentBuilder.createStudent(studentId, studentName, marks));

        StudentChecker.checkStudentExists(studentId);
        StudentChecker.verifyStudentData(studentId, studentName, marks);
    }

    @Test
    @DisplayName("Студента с данным ID в базе нет")
    void studentNotFound() {

        Assertions.assertThrows(NoSuchElementException.class, () -> studentsApi.getStudent(studentId));
    }

    @Test
    @DisplayName("Не найден лучший, если студентов в базе нет")
    void topStudentNotFound() {

        List<StudentDto> topStudents = studentsApi.getTopStudents();
        Assertions.assertTrue(topStudents.isEmpty());
    }

    @Test
    @DisplayName("Не найден лучший, т.к. ни у кого из студентов в базе нет оценок")
    void topStudentNotFoundBecauseDoNotHaveMarks() {

        studentsApi.addStudent(StudentBuilder.createStudent(null, studentName, null));
        List<StudentDto> topStudents = studentsApi.getTopStudents();

        Assertions.assertTrue(topStudents.isEmpty());

    }

    @Test
    @DisplayName("Найден лучший студент, если у него максимальная средняя оценка")
    void topStudentFound() {

        int[] topMarks = new int[]{4, 4, 5};
        String topStudentName = StudentDataGenerator.generateName();

        studentsApi.addStudent(StudentBuilder.createStudent(studentId, studentName, marks));
        int secondStudentId = studentsApi
                .addStudent(StudentBuilder.createStudent(null, topStudentName, topMarks));
        System.out.println("studentId");
        System.out.println(studentId);
        System.out.println("secondStudentId");
        System.out.println(secondStudentId);
        List<StudentDto> topStudents = studentsApi.getTopStudents();

        assertThat(topStudents).hasSize(1);
        StudentDto topStudent = topStudents.get(0);
        assertThat(topStudent.getName()).isEqualTo(topStudentName);

        studentsApi.deleteStudent(secondStudentId);
    }

    @Test
    @DisplayName("Найдено несколько лучших студентов, если у них всех эта оценка максимальная и при этом они равны по количеству оценок")
    void topStudentFoundMoreThanOne() {

        studentsApi
                .addStudent(StudentBuilder.createStudent(studentId, studentName, marks));
        int secondStudentId = studentsApi
                .addStudent(StudentBuilder.createStudent(null, studentName, marks));
        List<StudentDto> topStudents = studentsApi.getTopStudents();

        assertThat(topStudents).hasSize(2);

        studentsApi.deleteStudent(secondStudentId);
    }
}
