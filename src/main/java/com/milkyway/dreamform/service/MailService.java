package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.MailDto;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "cocody2022@gmail.com";

    public void mailSend(MailDto mailDto) {
        String str = getTempPassword(); //임시 비밀번호
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject("[코디] 임시 비밀번호");
        message.setText("코디 회원님, 임시 비밀번호를 발송드립니다."+"임시 비밀번호는 " + str + "입니다.");
        mailSender.send(message);
        updatePassword(str, mailDto.getEmail());
    }

    //비밀번호 변경
    public void updatePassword(String str, String email) {
        String pw = passwordEncoder.encode(str);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));
        user.updatePw(pw);
        userRepository.save(user);
        log.info("임시비번: " + user.getPassword());
    }

    //임시 비밀번호 생성
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
