package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.SignupRequestDto;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        String interest = requestDto.getInterest();
        String license = requestDto.getLicense();

        User user = new User(username, password, nickname, email, interest, license);
        userRepository.save(user);
    }

    }
