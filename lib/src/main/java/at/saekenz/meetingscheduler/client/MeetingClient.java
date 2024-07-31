package at.saekenz.meetingscheduler.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import at.saekenz.meetingscheduler.api.model.EHTTPRequest;
import at.saekenz.meetingscheduler.api.model.Meeting;
import at.saekenz.meetingscheduler.api.model.Response;
import at.saekenz.meetingscheduler.api.model.User;
import reactor.core.publisher.Mono;

public class MeetingClient {

	private static final String URL = "http://localhost:8080/api/meetings";

	private static final int VALID_TIME_ID = 0;
	private static final int SEC_VALID_TIME_ID = 1;
	private static final int TERT_VALID_TIME_ID = 2;
	private static final int INVALID_TIME_ID = 999;

	private static final int VALID_MEETING_ID = 1;
	private static final int SEC_VALID_MEETING_ID = 2;
	private static final int TERT_VALID_MEETING_ID = 3;
	private static final int INVALID_MEETING_ID = 999;

	private static final WebClient.Builder builder = WebClient.builder();

	// Run an example client testing the different meeting manipulation methods
	public static void main(String[] args) {

		// Retrieve all meetings
		retrieveAllMeetings();

		// Create a new meeting with several proposed times
		String createdMeetingId = proposeMeeting();

		// Retrieve newly created meeting
		retrieveMeetingById(Integer.parseInt(createdMeetingId));

		// Add time to meeting with Id = 1
		addTimeToMeeting(VALID_MEETING_ID);

		// Retrieve meeting by Id (valid Id = 1)
		retrieveMeetingById(VALID_MEETING_ID);

		// Retrieve meeting by Id (invalid Id = 999)
		retrieveMeetingById(INVALID_MEETING_ID);

		// Retrieve all meetings again
		retrieveAllMeetings();

		// Remove time from meeting (valid id = 2)
		removeTimeFromMeeting(SEC_VALID_MEETING_ID, VALID_TIME_ID);

		// Remove time from meeting (valid id = 2)
		removeTimeFromMeeting(SEC_VALID_MEETING_ID, SEC_VALID_TIME_ID);

		// Remove time from meeting (invalid id = 999)
		removeTimeFromMeeting(SEC_VALID_MEETING_ID, INVALID_TIME_ID);

		// Retrieve all meetings again
		retrieveAllMeetings();

		// Publish meeting (valid id = 1)
		publishMeeting(VALID_MEETING_ID);

		// Publish meeting (invalid id = 999)
		publishMeeting(INVALID_MEETING_ID);

		// Add User to meeting time (valid ids)
		User user = new User("John", "Doe", "john.doe@example.com");
		addUserToTime(TERT_VALID_MEETING_ID, SEC_VALID_TIME_ID, user);

		// Add User to meeting time (valid ids)
		addUserToTime(TERT_VALID_MEETING_ID, TERT_VALID_TIME_ID, user);

		// Add User to meeting time (valid ids)
		user = new User("Jane", "Doe", "jane.doe@example.com");
		addUserToTime(TERT_VALID_MEETING_ID, TERT_VALID_TIME_ID, user);

		// Add User to meeting time (invalid timeId = 999)
		addUserToTime(TERT_VALID_MEETING_ID, INVALID_TIME_ID, user);

		// Add User to meeting time (invalid meetingId = 999)
		addUserToTime(INVALID_MEETING_ID, TERT_VALID_TIME_ID, user);

		// Retrieve all meetings again
		retrieveAllMeetings();

	}

	private static void publishMeeting(int meetingId) {
		String url = URL + "/" + meetingId + "/times";
		Response response = builder.build().get().uri(url).retrieve().bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.GET);
	}

	private static void removeTimeFromMeeting(int meetingId, int timeId) {
		String url = URL + "/" + meetingId + "/times/" + timeId;
		Response response = builder.build().delete().uri(url).retrieve().bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.DELETE);
	}

	private static void retrieveMeetingById(int meetingId) {

		String url = URL + "/" + meetingId;
		Response response = builder.build().get().uri(url).retrieve().bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.GET);
	}

	private static void addTimeToMeeting(int meetingId) {
		String proposedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String url = URL + "/" + meetingId + "/times";
		Response response = builder.build().post().uri(url).body(Mono.just(proposedTime), String.class).retrieve()
				.bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.POST);
	}

	private static String proposeMeeting() {
		Meeting meeting = createMeeting();
		Response response = builder.build().post().uri(URL).body(Mono.just(meeting), Meeting.class).retrieve()
				.bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.POST);
		return response.getData().toString();
	}

	private static void addUserToTime(int meetingId, int timeId, User user) {
		String url = URL + "/" + meetingId + "/times/" + timeId + "/users";
		Response response = builder.build().post().uri(url).body(Mono.just(user), User.class).retrieve()
				.bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.POST);
	}

	private static void retrieveAllMeetings() {
		Response response = builder.build().get().uri(URL).retrieve().bodyToMono(Response.class).block();
		printResponse(response, EHTTPRequest.GET);
	}

	private static void printResponse(Response response, EHTTPRequest request) {
		System.out.println("=========================================================");
		checkRequestType(request);
		System.out.println(response);
		System.out.println("=========================================================");
	}

	private static void checkRequestType(EHTTPRequest request) {
		switch (request) {
			case GET, POST, DELETE:
				System.out.println("HTTP Method: " + request + " - " + request.getMessage());
				break;
			default:
				System.out.println("Unknown HTTP Method: " + request.getMessage());
		}
	}

	private static Meeting createMeeting() {
		List<String> times = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		times.add(LocalDateTime.now().format(formatter));
		times.add(LocalDateTime.now().plusHours(1).format(formatter));
		times.add(LocalDateTime.now().plusHours(2).format(formatter));
		times.add(LocalDateTime.now().plusHours(3).format(formatter));
		Meeting meeting = new Meeting();
		meeting.setId(null);
		meeting.putTimes(times);
		return meeting;
	}

}
