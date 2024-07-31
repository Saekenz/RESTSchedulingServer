package at.saekenz.meetingscheduler.api.model;

import java.util.List;

public class Response {

	private EHTTPStatus status;
	private Object data;

	public Response(EHTTPStatus status, Object data) {
		this.status = status;
		this.data = data;
	}

	public Response() {

	}

	public EHTTPStatus getStatus() {
		return status;
	}

	public Object getData() {
		return data;
	}

	public void setStatus(EHTTPStatus status) {
		this.status = status;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		if (data instanceof List<?> meetings) {
            StringBuilder sb = new StringBuilder();
			sb.append("API-Response: ").append(status.getCode()).append(" ").append(status);
			for (Object m : meetings) {
				sb.append("\n").append("Meeting: ").append(m);
			}
			return sb.toString();
		} else
			return "API-Response: " + status.getCode() + " " + status + "\nMeeting: " + data;
	}

}
