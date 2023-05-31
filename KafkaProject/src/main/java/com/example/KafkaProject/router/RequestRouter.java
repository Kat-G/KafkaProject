package com.example.KafkaProject.router;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;

import com.example.KafkaProject.generated.Employee;
import com.example.KafkaProject.generated.ObjectFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class RequestRouter extends RouteBuilder {
    @Value("${kafka-requests-path}")
    private String from_path;

    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        JaxbDataFormat jaxb = new JaxbDataFormat(jaxbContext);

        onException(UnmarshalException.class)
                .handled(true)
                .setBody(simple("<status>fail</status><message>There was an error in unmarshalling</message>"))
                .to("direct:status")
                .to("direct:metrics_fail")
                .to("direct:metrics_stop_timer");

        from(from_path)
                .to("direct:metrics_total_messages")
                .to("direct:metrics_start_timer")
                .unmarshal(jaxb)
                .choice()
                .when(body().isInstanceOf(Employee.class))
                .log("Message received from Kafka : ${body}")
                .log(" on the topic ${headers[kafka.TOPIC]}")
                .to("direct:save_to_db")
                .otherwise()
                .setBody(simple("<status>fail</status><message>XML data isn't instance of Employee</message>"))
                .to("direct:status")
                .to("direct:metrics_fail")
                .to("direct:metrics_stop_timer");

    }
}
