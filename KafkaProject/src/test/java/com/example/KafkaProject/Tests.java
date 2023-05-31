package com.example.KafkaProject;

import com.example.KafkaProject.entity.EmployeeTable;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest(properties = {"kafka-requests-path=direct:requests"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
public class Tests {

	@Autowired
	ProducerTemplate producerTemplate;

	@EndpointInject("mock:jpa:com.example.KafkaProject.entity.EmployeeTable")
	public MockEndpoint saveToDb;

	@EndpointInject("mock:kafka:results")
	public MockEndpoint kafkaResults;

	@EndpointInject("mock:kafka:status_topic")
	public MockEndpoint kafkaStatusTopic;

	@Test
	public void saveToDBTest() throws InterruptedException, ParseException {
		EmployeeTable employee = new EmployeeTable();
		employee.setName("Ivan Ivanov");
		employee.setPost("manager");
		employee.setAge(30);
		saveToDb.expectedBodiesReceived(employee);

		String body = """
		<?xml version="1.0" encoding="UTF-8"?>
    			<Employee>
    				<name>Ivan Ivanov</name>
    				<post>manager</post>
    				<email>ivanov@mail.ru</email>
    				<age>30</age>
    			</Employee>
		""";

		producerTemplate.sendBody("direct:requests", body);

		MockEndpoint.assertIsSatisfied(saveToDb);
	}

	@Test
	public void kafkaResultsTest() throws InterruptedException {
		String xml = """
		<?xml version="1.0" encoding="UTF-8"?>
    			<Employee>
    				<name>Petr Petrov</name>
    				<post>accountant</post>
    				<email>petrov@mail.ru</email>
    				<age>28</age>
    			</Employee>
		""";

		String json = "{\"name\":\"Petr Petrov\",\"post\":\"accountant\",\"age\":28\"}";

		kafkaResults.expectedBodiesReceived(json);
		producerTemplate.sendBody("direct:requests", xml);

		MockEndpoint.assertIsSatisfied(kafkaResults);
	}

	@Test
	public void sendOKStatusTest() throws InterruptedException {
		kafkaStatusTopic.expectedBodiesReceived("<status>OK</status>");

		String body = """
		<?xml version="1.0" encoding="UTF-8"?>
    			<Employee>
    				<name>Misha Testov</name>
    				<post>manager</post>
    				<email>testov@mail.ru</email>
    				<age>29</age>
    			</Employee>
		""";

		producerTemplate.sendBody("direct:requests", body);

		kafkaStatusTopic.assertIsSatisfied(5000);
	}

	@Test
	public void sendErrorStatusTest() throws InterruptedException {
		kafkaStatusTopic.expectedBodiesReceived("<status>fail</status><message>There was an error in unmarshalling</message>");

		producerTemplate.sendBody("direct:requests", "Something");

		kafkaStatusTopic.assertIsSatisfied(5000);
	}
}
