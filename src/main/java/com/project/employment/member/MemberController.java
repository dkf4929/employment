package com.project.employment.member;

import com.project.employment.attach.AttachFileDto;
import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        return "member/popup/school-search";
    }

    @PostMapping("/add")
    public void save(@Validated MemberUpsertRq memberUpsertRq) {
        memberService.save(MemberDto.from(memberUpsertRq));
    }
//
//    @GetMapping("{memberId}")
//    public String edit(Model model, @PathVariable Long memberId, HttpServletResponse response) throws IOException {
//        Member loginMember = memberService.findById(memberId);
//        Member principal = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (!principal.getId().equals(memberId)) response.sendRedirect("/");
//
//        model.addAttribute("member", loginMember);
//
//        if (loginMember.getImageFile() != null) {
//            model.addAttribute("file", Base64.getEncoder().encodeToString(loginMember.getImageFile().getFile()));
//        }
//
//        return "member/edit";
//    }
//
//    @PatchMapping
//    @ResponseBody
//    public String edit(@Valid MemberUpdateRq dto) {
//        memberService.edit(dto);
//
//        return "저장이 완료되었습니다.";
//    }
}
