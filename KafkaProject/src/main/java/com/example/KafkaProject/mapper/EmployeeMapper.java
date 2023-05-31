package com.example.KafkaProject.mapper;

import com.example.KafkaProject.dto.DTO;
import com.example.KafkaProject.entity.EmployeeTable;
import com.example.KafkaProject.generated.Employee;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "age", source = "age")
    DTO mapWithoutId(EmployeeTable Employee);

    @Mapping(source = "name", target = "name")
    @Mapping(target = "post", source = "post")
    @Mapping(source = "age", target = "age")
    EmployeeTable mapGenerated(Employee generated);
}
