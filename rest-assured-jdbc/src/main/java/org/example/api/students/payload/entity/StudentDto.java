package org.example.api.students.payload.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("marks")
    @Builder.Default
    private int[] marks = new int[0];
}
