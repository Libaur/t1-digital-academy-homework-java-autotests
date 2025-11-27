package org.example;

import custom_tags.Smoke;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentTests {

    private final String DEFAULT_NAME = "Alex";
    private Student stud;

    @BeforeEach
    void setUp() {
        stud = new Student(DEFAULT_NAME);
    }

    @Test
    @DisplayName("Получить имя")
    public void checkGetName() {

        assertEquals(DEFAULT_NAME, stud.getName());
    }

    @Test
    @Smoke
    @DisplayName("Изменить имя")
    public void checkSetName() {

        String newName = "Innokentiy";
        stud.setName(newName);
        assertEquals(newName, stud.getName());
    }

    @DisplayName("Добавить валидные значения грейда")
    @RepeatedTest(value = 4, name = "Валидный грейд {currentRepetition} из {totalRepetitions}")
    public void checkValidGradeValues(RepetitionInfo repetitionInfo) {

        int grade = repetitionInfo.getCurrentRepetition() + 1;
        stud.addGrade(grade);
        assertEquals(grade, stud.getGrades().get(0));
    }

    @DisplayName("Добавить невалидные значения грейда")
    @ParameterizedTest(name = "Добавить невалидное значение грейда {0}")
    @ValueSource(ints = {1, 0, 6, 9})
    public void checkInvalidGradeValues(int grade) {

        String expectedMessage = grade + " is wrong grade";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> stud.addGrade(grade)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Инкапсуляция списка грейдов")
    public void checkGradesEncapsulation(){

        assertThrows(UnsupportedOperationException.class, () -> stud.getGrades().add(2));
    }

    @Test
    @DisplayName("Сравнение студентов")
    void checkStudentsEquals() {

        Utils utils = new Utils();

        stud.addGrade(3);
        stud.addGrade(4);

        Student stud2 = utils.createStudent(DEFAULT_NAME, 3,4);
        Student stud3 = utils.createStudent("Innokentiy", 3,4);
        Student stud4 = utils.createStudent(DEFAULT_NAME, 4,3);

        Assertions.assertAll( "Совпадение",
                () -> assertEquals(stud, stud2),
                () -> assertEquals(stud.hashCode(), stud2.hashCode())
        );

        Assertions.assertAll("Несовпадение",
                () -> assertNotEquals(stud, stud3),
                () -> assertNotEquals(stud2, stud4),
                () -> assertNotEquals(stud3.hashCode(), stud4.hashCode())
        );
    }

    @Test
    @DisplayName("Конвертация данных о студенте в строку")
    void checkConvertStudentToString() {

        stud.addGrade(5);

        assertEquals("Student{name=Alex, marks=[5]}", stud.toString());
    }
}
