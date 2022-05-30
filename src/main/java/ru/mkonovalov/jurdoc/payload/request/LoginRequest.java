package ru.mkonovalov.jurdoc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {
	@NotBlank
	private String username;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginRequest that = (LoginRequest) o;
		return Objects.equals(username, that.username) && Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);
	}
}
