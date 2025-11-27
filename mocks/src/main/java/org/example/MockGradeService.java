package org.example;

import java.util.Set;

public class MockGradeService implements GradeService {
    private final Set<Integer> validGrades;

    public MockGradeService() {
        this.validGrades = Set.of(2, 3, 4, 5);
    }

    @Override
    public boolean checkIsValidGrade(int grade) {
        return validGrades.contains(grade);
    }
}
