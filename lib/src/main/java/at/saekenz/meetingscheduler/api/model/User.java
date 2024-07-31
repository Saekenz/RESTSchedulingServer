package at.saekenz.meetingscheduler.api.model;

import java.util.Objects;
import java.util.UUID;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private UUID userId;

	public User() {

	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userId = UUID.randomUUID();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public UUID getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "User = [" + firstName + " " + lastName + ", " + email + ", ID = " + userId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(userId, other.userId);
	}

}
