package at.saekenz.meetingscheduler.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "at.saekenz.meetingscheduler.service", "at.saekenz.meetingscheduler.api.controller" })
public class MeetingServer {

	public static void main(String[] args) {
		SpringApplication.run(MeetingServer.class, args);

	}

}
