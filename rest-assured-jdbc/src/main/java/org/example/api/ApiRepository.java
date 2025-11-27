package org.example.api;

import lombok.experimental.UtilityClass;
import org.example.api.students.StudentsApi;

@UtilityClass
public class ApiRepository {

    public final StudentsApi studentsApi = new StudentsApi();
}
