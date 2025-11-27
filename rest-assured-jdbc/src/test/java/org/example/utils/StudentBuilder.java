package org.example.utils;

import lombok.experimental.UtilityClass;
import org.example.api.students.payload.entity.StudentDto;

@UtilityClass
public class StudentBuilder {

    public StudentDto createStudent(Integer id, String name, int[] marks) {

        return StudentDto.builder()
                .id(id)
                .name(name)
                .marks(marks)
                .build();
    }
}
