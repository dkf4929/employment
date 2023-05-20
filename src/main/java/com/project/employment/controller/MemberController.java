package com.project.employment.controller;

import com.project.employment.dto.MemberSaveDto;
import com.project.employment.dto.MemberUpdateDto;
import com.project.employment.entity.Member;
import com.project.employment.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/add")
    public String addPage() {
        return "member/add";
    }

    @ResponseBody
    @GetMapping("/id_check")
    public ResponseEntity<Integer> checkDuplicate(String loginId) {
        return memberService.checkLoginIdDuplicate(loginId);
    }

    @GetMapping("/school-search")
    public String schoolSearchPage() {
        return "member/popup/school-search";
    }

    @PostMapping("/add")
    public void save(@Valid MemberSaveDto dto) throws IOException {
        memberService.save(dto);
    }

    @GetMapping("/{memberId}")
    public String edit(Model model) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("member", loginMember);
        model.addAttribute("file", Base64.getEncoder().encodeToString(loginMember.getFile()));

        return "member/edit";
    }

    @PatchMapping("/edit")
    @ResponseBody
    public String edit(@Valid MemberUpdateDto dto, BindingResult bindingResult) {
        memberService.edit(dto, bindingResult);

        return "저장이 완료되었습니다.";
    }
}
