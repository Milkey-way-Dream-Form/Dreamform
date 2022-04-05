package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.SignupRequestDto;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        String interest = requestDto.getInterest();
        String license = requestDto.getLicense();

        User user = new User(username, password, nickname, email, interest, license);
        userRepository.save(user);
    }

    }
