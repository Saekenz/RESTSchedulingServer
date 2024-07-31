package at.saekenz.meetingscheduler.api.model;

public enum EHTTPStatus {
	OK(200, "OK"), Created(201, "Created"), NoContent(204, "No Content"), NotModified(304, "Not Modified"),
	BadRequest(400, "Bad Request"), Unauthorized(401, "Unauthorized"), Forbidden(403, "Forbidden"),
	NotFound(404, "Not Found"), InternalServerError(500, "Internal Server Error");

	private final int code;
	private final String message;

	EHTTPStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
