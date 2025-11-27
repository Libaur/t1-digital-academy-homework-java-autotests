package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class StudentTests {

    private final String DEFAULT_NAME = "Alex";
    private Student stud;
    private MockGradeService mockGradeService;

    @BeforeEach
    void setUp() {
        mockGradeService = new MockGradeService();
        RatingService mockRatingService = sum -> sum * 10;
        stud = new Student(DEFAULT_NAME, mockGradeService, mockRatingService);
    }

    @DisplayName("Добавить валидные значения грейда")
    @RepeatedTest(value = 4, name = "Валидный грейд {currentRepetition} из {totalRepetitions}")
    public void checkValidGradeValues(RepetitionInfo repetitionInfo) {

        int grade = repetitionInfo.getCurrentRepetition() + 1;
        stud.addGrade(grade);
        Assertions.assertEquals(grade, stud.getGrades().get(0));
    }

    @DisplayName("Добавить невалидные значения грейда")
    @ParameterizedTest(name = "Добавить невалидное значение грейда {0}")
    @ValueSource(ints = {1, 0, 6, 9})
    public void checkInvalidGradeValues(int grade) {

        String expectedMessage = grade + " is wrong grade";

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> stud.addGrade(grade)
        );
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
