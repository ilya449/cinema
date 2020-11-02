package com.cinema.model.dto.user;

import com.cinema.annotation.EmailConstraint;
import com.cinema.annotation.FieldsConstraint;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@FieldsConstraint(field = "password",
        fieldMatch = "repeatedPassword",
        message = "Passwords aren't the same!")
public class UserRequestDto {
    @EmailConstraint
    private String email;
    @Size(min = 4, max = 16)
    private String password;
    @Size(min = 4, max = 16)
    private String repeatedPassword;
}
