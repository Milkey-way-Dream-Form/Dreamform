package com.milkyway.dreamform.validator;

import com.milkyway.dreamform.dto.SignupRequestDto;
import com.milkyway.dreamform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpRequestDtoValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignupRequestDto.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignupRequestDto signUpRequestDto = (SignupRequestDto) o;

        if(userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            errors.rejectValue("username", "invalid.username", new Object[]{signUpRequestDto.getUsername()},"이미 사용중인 닉네임 입니다");
        }
        if(userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpRequestDto.getEmail()},"이미 사용중인 이메일 입니다");
        }

        if(signUpRequestDto.getPassword().equals(signUpRequestDto.getUsername())) {
            errors.rejectValue("password", "wrong.value", "닉네임과 패스워드는 같을 수 없습니다.");
        }
        if(!signUpRequestDto.getPassword().equals(signUpRequestDto.getPasswordConfirm())) {
            errors.rejectValue("password", "wrong.value", "입력한 패스워드가 서로 일치하지 않습니다.");
        }
    }
}
