package ru.mkonovalov.jurdoc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter implements Serializable {
    private String usernameLike;
    private String emailLike;
    private String firstNameLike;
    private String lastNameLike;
    private String middleNameLike;
    private String roleIs;
}
