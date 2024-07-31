package at.saekenz.meetingscheduler.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.saekenz.meetingscheduler.api.model.EHTTPStatus;
import at.saekenz.meetingscheduler.api.model.Meeting;
import at.saekenz.meetingscheduler.api.model.Response;
import at.saekenz.meetingscheduler.api.model.User;
import at.saekenz.meetingscheduler.service.MeetingServiceImpl;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

	private final MeetingServiceImpl meetingService;
	private static final String URL = "http://localhost:8080/api/meetings/";

	@Autowired
	public MeetingController(MeetingServiceImpl meetingService) {
		this.meetingService = meetingService;
	}

	@PostMapping
	public Response createMeeting(@RequestBody Meeting meeting) {
		String createdMeetingId = meetingService.createMeeting(meeting);
		return new Response(EHTTPStatus.Created, createdMeetingId);
	}

	@PostMapping("/{id}/times")
	public Response addTime(@PathVariable("id") Integer id, @RequestBody String time) {
		meetingService.addTimeToMeeting(id, time);
		return new Response(EHTTPStatus.Created, time);
	}

	@GetMapping("/{id}")
	public Response getMeeting(@PathVariable("id") Integer id) {
		Meeting foundMeeting = meetingService.findMeetingById(id);
		if (foundMeeting != null)
			return new Response(EHTTPStatus.OK, foundMeeting);
		else
			return new Response(EHTTPStatus.NotFound, EHTTPStatus.NotFound.getCode());
	}

	@GetMapping
	public Response getAllMeetings() {
		List<Meeting> meetings = meetingService.findAllMeetings();
		return new Response(EHTTPStatus.OK, meetings);
	}

	@DeleteMapping("/{meetingId}/times/{timeId}")
	public Response removeTime(@PathVariable("meetingId") Integer meetingId, @PathVariable("timeId") Integer timeId) {
		String removedTime = meetingService.removeTimeFromMeeting(meetingId, timeId);
		if (removedTime != null)
			return new Response(EHTTPStatus.OK, removedTime);
		else
			return new Response(EHTTPStatus.NotFound, EHTTPStatus.NotFound.getCode());
	}

	@GetMapping("/{meetingId}/times/{timeId}")
	public Response getTime(@PathVariable("meetingId") Integer meetingId, @PathVariable("timeId") Integer timeId) {
		String foundTime = meetingService.findTimeFromMeetingById(meetingId, timeId);
		if (foundTime != null)
			return new Response(EHTTPStatus.OK, foundTime);
		else
			return new Response(EHTTPStatus.NotFound, EHTTPStatus.NotFound.getCode());
	}

	@PostMapping("/{meetingId}/times/{timeId}/users")
	public Response getTime(@PathVariable("meetingId") Integer meetingId, @PathVariable("timeId") Integer timeId,
			@RequestBody User user) {
		boolean successfullyAdded = meetingService.addParticipantToTime(meetingId, timeId, user);
		if (successfullyAdded)
			return new Response(EHTTPStatus.OK,
					user + " has chosen timeslot (" + timeId + ") in meeting (" + meetingId + ")");
		else
			return new Response(EHTTPStatus.InternalServerError, EHTTPStatus.InternalServerError.getCode());
	}

	@GetMapping("/{id}/times")
	public Response publishMeetingTimes(@PathVariable("id") Integer id) {
		List<String> times = meetingService.findAllTimesFromMeetingById(id);
		if (times != null) {
			List<String> timeLinks = new ArrayList<>();
			for (int i = 0; i < times.size(); i++) {
				timeLinks.add(URL + id + "/times/" + i);
			}
			return new Response(EHTTPStatus.OK, timeLinks);
		} else
			return new Response(EHTTPStatus.NotFound, EHTTPStatus.NotFound.getCode());
	}

}
