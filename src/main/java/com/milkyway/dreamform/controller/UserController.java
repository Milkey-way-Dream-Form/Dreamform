package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.MailDto;
import com.milkyway.dreamform.dto.SignupRequestDto;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.service.MailService;
import com.milkyway.dreamform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/";
    }

    //카카오 로그인
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);

        return "redirect:/";
    }

    //아이디 찾기 페이지
    @GetMapping("/user/findid")
    public String findIdForm() {
        return "findIdForm";
    }

    //아이디 찾기
    @PostMapping("/user/findid")
    public String findId(@ModelAttribute("form") SignupRequestDto requestDto, Model model) {
        String username = userService.findByUsername(requestDto.getEmail());
        model.addAttribute("user", username);
        return "findId";
    }

    //비밀번호 찾기 페이지
    @GetMapping("/user/findpw")
    public String findPasswordForm() {
        return "findPwForm";
    }

    //비밀번호 찾기
    @PostMapping("/user/findpw")
    public void findPw(MailDto mailDto) {
        mailService.mailSend(mailDto);
    }
}
