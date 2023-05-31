package com.example.KafkaProject.router;

import com.example.KafkaProject.dto.DTO;
import com.example.KafkaProject.entity.EmployeeTable;
import com.example.KafkaProject.generated.Employee;
import com.example.KafkaProject.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveRouter extends RouteBuilder {
    private final EmployeeMapper mapper;

    @Override
    public void configure() {
        from("direct:save_to_db")
                .choice()
                .when(body().isInstanceOf(Employee.class))
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .process(exchange -> {
                    Employee in = exchange.getIn().getBody(Employee.class);
                    EmployeeTable employee = mapper.mapGenerated(in);

                    exchange.getMessage().setBody(employee, EmployeeTable.class);
                })
                .log("Saving ${body} to database")
                .to("jpa:com.example.KafkaProject.entity.EmployeeTable")
                .process(exchange -> {
                    EmployeeTable in = exchange.getIn().getBody(EmployeeTable.class);
                    DTO employee = mapper.mapWithoutId(in);

                    exchange.getMessage().setBody(employee, DTO.class);
                })
                .marshal().json(JsonLibrary.Jackson)
                .log("Saving ${body} to kafka")
                .to("kafka:results?brokers=localhost:9092")
                .setBody(simple("<status>OK</status>"))
                .to("direct:status")
                .to("direct:metrics_success")
                .to("direct:metrics_stop_timer")
                .otherwise()
                .setBody(simple("<status>fail</status><message>XML data isn't instance of Employee</message>"))
                .to("direct:status")
                .to("direct:metrics_fail")
                .to("direct:metrics_stop_timer");
    }
}