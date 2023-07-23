package com.project.employment.controller;

import com.project.employment.common.HttpSuplier;
import com.project.employment.dto.MemberUpdateRq;
import com.project.employment.entity.Member;
import com.project.employment.exception.BusinessException;
import com.project.employment.exception.CommonException;
import com.project.employment.service.MemberService;
import com.project.request.MemberRq;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
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
    public void save(@Valid MemberRq memberRq) {
        memberService.saveMember(memberRq);
    }

    @GetMapping("{memberId}") //로그인한 유저가 다른 아이디로 url에 강제로 id입력 시 에러처리 필요.
    public String edit(Model model, @PathVariable Long memberId, HttpServletResponse response) throws IOException {
        Member loginMember = memberService.findById(memberId);
        Member principal = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!principal.getId().equals(memberId)) {
            response.sendRedirect("/");

            throw new BusinessException("인증 에러");
        }

        model.addAttribute("member", loginMember);

        if (!ObjectUtils.isEmpty(loginMember.getImageFile())) model.addAttribute("file", Base64.getEncoder().encodeToString(loginMember.getImageFile().getFile()));

        return "member/edit";
    }

    @PatchMapping
    @ResponseBody
    public void edit(@Valid MemberUpdateRq dto) throws IOException {
        memberService.edit(dto);
    }
}
