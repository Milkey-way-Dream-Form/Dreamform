package com.milkyway.dreamform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String interest;
    private String license;

}
