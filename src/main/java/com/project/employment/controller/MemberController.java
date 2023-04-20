package com.project.employment.controller;

import com.project.employment.dto.MemberSaveDto;
import com.project.employment.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public void save(@Valid MemberSaveDto dto) {
        memberService.save(dto);
    }
}
