package com.milkyway.dreamform.dto;

import com.milkyway.dreamform.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Length(min = 3, max= 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]{3,20}$")
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 4, max = 50)
    private String password;

    @NotBlank
    @Length(min = 4, max = 50)
    private String passwordConfirm;
}
