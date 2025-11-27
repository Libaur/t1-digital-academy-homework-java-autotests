package org.example.api.students;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.example.api._base._BaseApi;
import org.example.api.students.payload.entity.StudentDto;
import org.example.env.Env;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class StudentsApi extends _BaseApi {

    public StudentsApi() {
        super(Env.API.API_CONFIG);
    }

    public StudentDto getStudent(int studentId) {
        log.info("Получить студента с id {}", studentId);

        Response response = jsonAutoAuth()
                .basePath("/student/" + studentId)
                .get();

        if (response.getStatusCode() == 404) {
            throw new NoSuchElementException("Не найден студент с id: " + studentId);
        }

        return response.as(StudentDto.class);
    }

    public int addStudent(StudentDto student) {
        log.info("Добавление студента по имени {}", student.getName());

        Response response = jsonAutoAuth()
                .basePath("/student")
                .body(student)
                .post();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        switch (statusCode) {
            case 201:
                if (responseBody == null || responseBody.trim().isEmpty()) {
                    log.info("Студент создан/обновлен, сервер вернул пустой ответ");
                    return statusCode;
                }
                    return response.as(int.class);

            case 400:
                throw new IllegalArgumentException("Ошибка валидации: Имя студента не заполнено");

            default:
                throw new RuntimeException("Ошибка: " + statusCode);
        }
    }

    public int deleteStudent(int studentId) {

        log.info("Удаление студента c id {}", studentId);

        Response response = jsonAutoAuth()
                .basePath("/student/" + studentId)
                .delete();

        return response.statusCode();
    }

    public List<StudentDto> getTopStudents() {
        log.info("Получить лучших студентов");

        Response response = jsonAutoAuth()
                .basePath("/topStudent")
                .get();

        response.then().statusCode(200);

        String responseBody = response.getBody().asString();

        if (responseBody.startsWith("{")) {
            return Collections.singletonList(response.as(StudentDto.class));
        }
        if (responseBody.startsWith("[")) {
            return Arrays.asList(response.as(StudentDto[].class));
        }
        return Collections.emptyList();
    }
}
