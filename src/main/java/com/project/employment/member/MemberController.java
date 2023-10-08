package com.project.employment.member;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    @GetMapping("/id-check")
    public ResponseEntity<Integer> checkDuplicate(String loginId) {
        return memberService.checkLoginIdDuplicate(loginId);
    }

    @GetMapping("/school-search")
    public String schoolSearchPage() {
        return "/popup/school-search";
    }

    @PostMapping("/add")
    public void save(@Validated MemberInsertRq memberInsertRq) {
        memberService.save(MemberDto.from(memberInsertRq));
    }

    @GetMapping("/{memberId}")
    public String edit(Model model, @PathVariable Long memberId, HttpServletResponse response) throws IOException {
        Member loginMember = memberService.findById(memberId);
        Member principal = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!principal.getId().equals(memberId)) response.sendRedirect("/");

        model.addAttribute("member", loginMember);

        return "member/edit";
    }

    @PutMapping
    @ResponseBody
    public void edit(@Valid MemberUpdateRq rq) {
        memberService.edit(MemberDto.from(rq));
    }
}
