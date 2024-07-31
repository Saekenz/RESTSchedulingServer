package at.saekenz.meetingscheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import at.saekenz.meetingscheduler.api.model.User;
import org.springframework.stereotype.Service;

import at.saekenz.meetingscheduler.api.model.Meeting;

@Service
public class MeetingServiceImpl implements IMeetingService {

	private static final int NUM_GENERATED_MEETINGS = 4;
	private static final int NUM_GENERATED_TIMES_PER_MEETING = 3;

	private final List<Meeting> meetings;

	private int idCounter = 0;

	public MeetingServiceImpl() {
		meetings = new ArrayList<>();
		initMeetings(NUM_GENERATED_MEETINGS);
	}

	@Override
	public Meeting findMeetingById(int id) {
		try {
			Optional<Meeting> meetingOptional = meetings.stream()
					.filter(meeting -> meeting.getId().equals(String.valueOf(id))).findFirst();
			if (meetingOptional.isPresent()) {
				return meetingOptional.get();
			} else
				throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error while trying to fetch resource: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Meeting> findAllMeetings() {
		return meetings;
	}

	@Override
	public String createMeeting(Meeting meeting) {
		String meetingId = idCounter + "";
		meeting.setId(meetingId);
		meetings.add(meeting);
		idCounter++;
		return meetingId;
	}

	@Override
	public void addTimeToMeeting(int id, String time) {
		meetings.get(id).addTime(time);
	}

	@Override
	public String removeTimeFromMeeting(int meetingId, int timeId) {
		try {
			return meetings.get(meetingId).removeTimeById(timeId);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error while trying to delete resource: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> findAllTimesFromMeetingById(int meetingId) {
		Meeting meeting = findMeetingById(meetingId);
		if (meeting != null)
			return meeting.fetchTimes();
		else
			return null;
	}

	@Override
	public String findTimeFromMeetingById(int meetingId, int timeId) {
		try {
			List<String> meetingTimes = findAllTimesFromMeetingById(meetingId);
			if (meetingTimes != null) {
				return meetingTimes.get(timeId);
			} else
				throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error while trying to fetch resource: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean addParticipantToTime(int meetingId, int timeId, User user) {
		try {
			Meeting meeting = findMeetingById(meetingId);
			String time = meeting.fetchTimeById(timeId);
			meeting.addParticipantToMeetingTime(time, user);
			return true;
		} catch (Exception e) {
			System.err.println("Error while trying to add participant to time slot: " + e.getMessage());
			return false;
		}
	}

	private void initMeetings(int numberOfMeetings) {

		for (int i = 0; i < numberOfMeetings; i++) {
			Meeting meeting = new Meeting();
			meeting.putTimes(generateRandomTimes(NUM_GENERATED_TIMES_PER_MEETING));
			createMeeting(meeting);
		}

	}

	private List<String> generateRandomTimes(int numberOfTimes) {
		List<String> times = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Random random = new Random();

		for (int i = 0; i < numberOfTimes; i++) {
			LocalDateTime randomDateTime = LocalDateTime.now().plusDays(random.nextInt(30))
					.plusHours(random.nextInt(24)).plusMinutes(random.nextInt(60)).plusSeconds(random.nextInt(60));
			String formattedDate = randomDateTime.format(formatter);
			times.add(formattedDate);
		}
		return times;
	}

}
