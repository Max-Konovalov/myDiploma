package ru.mkonovalov.jurdoc.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements Serializable {
	private String message;

	public static MessageResponse of(String message) {
		return new MessageResponse(message);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MessageResponse that = (MessageResponse) o;
		return Objects.equals(message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(message);
	}
}
