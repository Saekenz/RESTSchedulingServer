package at.saekenz.meetingscheduler.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meeting {
	private String id;
	private Map<String, List<User>> timesWithParticipants;

	public Meeting() {

	}

	public Meeting(String id, List<String> proposedTimes) {
		this.id = id;
		putTimes(proposedTimes);
	}

	public Meeting(String id, Map<String, List<User>> timesWithParticipants) {
		this.id = id;
		this.timesWithParticipants = timesWithParticipants;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> fetchTimes() {
		List<String> allProposedTimes = new ArrayList<>();

        allProposedTimes.addAll(timesWithParticipants.keySet());

		return allProposedTimes;
	}

	public void putTimes(List<String> proposedTimes) {
		timesWithParticipants = new HashMap<>();
		if (proposedTimes != null && !proposedTimes.isEmpty()) {
			for (String time : proposedTimes) {
				addTime(time);
			}
		}
	}

	public Map<String, List<User>> getTimesWithParticipants() {
		return timesWithParticipants;
	}

	public void setTimesWithParticipants(Map<String, List<User>> timesWithParticipants) {
		this.timesWithParticipants = timesWithParticipants;
	}

	public void addTime(String time) {
		timesWithParticipants.put(time, new ArrayList<User>());
	}

	public String fetchTimeById(int id) {
		List<String> times = fetchTimes();
		return times.get(id);
	}

	public String removeTimeById(int id) {
		List<String> times = fetchTimes();
		String timeToBeRemoved = times.get(id);
		removeTime(timeToBeRemoved);
		return timeToBeRemoved;
	}

	public void removeTime(String timeToBeRemoved) {
		timesWithParticipants.remove(timeToBeRemoved);
	}

	public void addParticipantToMeetingTime(String time, User user) throws Exception {
		if (timesWithParticipants.containsKey(time)) {
			if (!timesWithParticipants.get(time).contains(user)) {
				timesWithParticipants.get(time).add(user);
			} else
				throw new Exception("User already registered for this time slot!");
		} else
			throw new Exception("To time slot available at: " + time + "!");
	}

}
