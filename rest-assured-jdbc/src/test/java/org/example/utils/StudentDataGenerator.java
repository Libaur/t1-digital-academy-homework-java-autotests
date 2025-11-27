package org.example.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StudentDataGenerator {
    private final Faker faker = new Faker();

    public int generateId() {
        return faker.number().numberBetween(1000, 99999);
    }

    public String generateName() {
        return faker.name().firstName();
    }
}