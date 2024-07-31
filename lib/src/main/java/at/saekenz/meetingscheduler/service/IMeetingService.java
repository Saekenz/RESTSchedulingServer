package at.saekenz.meetingscheduler.service;

import java.util.List;

import at.saekenz.meetingscheduler.api.model.User;
import at.saekenz.meetingscheduler.api.model.Meeting;

public interface IMeetingService {

	Meeting findMeetingById(int id);

	List<Meeting> findAllMeetings();

	String createMeeting(Meeting meeting);

	void addTimeToMeeting(int id, String time);

	String removeTimeFromMeeting(int meetingId, int timeId);

	String findTimeFromMeetingById(int meetingId, int timeId);

	List<String> findAllTimesFromMeetingById(int meetingId);

	boolean addParticipantToTime(int meetingId, int timeId, User user);

}
