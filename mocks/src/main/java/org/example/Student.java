package org.example;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Student {

    @Getter
    @Setter
    private String name;
    private List<Integer> grades = new ArrayList<>();
    private final GradeService gradeService;
    private final RatingService ratingService;

    public Student(String name, GradeService gradeService, RatingService ratingService) {
        this.name = name;
        this.gradeService = gradeService;
        this.ratingService = ratingService;
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    @SneakyThrows
    public void addGrade(int grade) {
        if (!gradeService.checkIsValidGrade(grade)) {
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
        grades.add(grade);
    }

    @SneakyThrows
    public int raiting() {
        int sum = grades.stream().mapToInt(x -> x).sum();
        return ratingService.calcRating(sum);
    }
}
