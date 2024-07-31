package at.saekenz.meetingscheduler.api.model;

public enum EHTTPRequest {
	GET("Retrieve specified resource(s)"), POST("Create specified resource"), DELETE("Remove specified resource");

	private final String message;

	EHTTPRequest(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
