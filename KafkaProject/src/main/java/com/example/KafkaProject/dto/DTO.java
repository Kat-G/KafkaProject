package com.example.KafkaProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("post")
    private String post;
    @JsonProperty("age")
    private Integer age;
}
