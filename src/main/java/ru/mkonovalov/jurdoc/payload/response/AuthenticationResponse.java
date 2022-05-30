package ru.mkonovalov.jurdoc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mkonovalov.jurdoc.domain.entity.ERole;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
	private Long id;
	private String username;
	private String email;

	private String firstName;
	private String lastName;
	private String middleName;

	private ERole role;

	private String token;
	private String type = "Bearer";

	public AuthenticationResponse(Long id, String username, String email, String firstName, String lastName, String middleName,  ERole role, String token) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.role = role;
		this.token = token;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", middleName='" + middleName + '\'' +
				", token='" + token + '\'' +
				", type='" + type + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuthenticationResponse that = (AuthenticationResponse) o;
		return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(token, that.token) && Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, firstName, lastName, middleName, token, type);
	}
}
