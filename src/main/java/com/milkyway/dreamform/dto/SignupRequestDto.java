package com.milkyway.dreamform.dto;

import com.milkyway.dreamform.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
}
