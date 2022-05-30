package ru.mkonovalov.jurdoc.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mkonovalov.jurdoc.domain.entity.ERole;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private ERole role;
}
