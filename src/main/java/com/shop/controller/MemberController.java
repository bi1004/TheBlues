package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;

@RequestMapping("/members") //공통경로
@Controller
@RequiredArgsConstructor
public class MemberController { //회원가입, 로그인 처리

    private final MemberService memberService; //회원가입과 관련된 비지니스 로직 처리
    private final PasswordEncoder passwordEncoder; //패스워드 암호화

    @GetMapping(value = "/new")
    public String memberForm(Model model){ //Model 객체를 사용해 memberFormDto라는 이름으로 빈 DTO 객체를 전달
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm"; //회원 가입 페이지로 이동
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        //@Valid 어노테이션을 사용해 memberFormDto 객체의 유효성 검사를 수행하고, BindingResult 객체를 사용해 검사 결과를 확인
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm"; //예외처리
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    } //로그인 페이지

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

}