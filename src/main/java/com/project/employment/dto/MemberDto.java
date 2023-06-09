package com.project.employment.dto;

import com.project.employment.entity.MemberImageFile;
import com.project.request.MemberRq;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@Getter
public class MemberDto {
    private Long memberId;

    private String loginId;

    private String password;

    private String email;

    private String memberName;

    private String birthday;

    private String phoneNumber;

    private String schoolName;

    private MemberImageFileDto memberImageFileDto;

    private String fileName;

    private String editYn;

    private String socialLoginYn;

    public static MemberDto from(MemberRq memberRq) {
        MemberDto memberDto = new MemberDto();

        memberDto.memberId = memberRq.getMemberId();
        memberDto.loginId = memberRq.getLoginId();
        memberDto.password = new BCryptPasswordEncoder().encode(memberRq.getPassword());
        memberDto.email = memberRq.getEmail();
        memberDto.memberName = memberRq.getMemberName();
        memberDto.birthday = memberRq.getBirthday();
        memberDto.phoneNumber = memberRq.getPhoneNumber();
        memberDto.schoolName = memberRq.getSchoolName();
        memberDto.memberImageFileDto = MemberImageFileDto.from(memberRq.getFile());
        memberDto.fileName = memberRq.getFile().getOriginalFilename();
        memberDto.editYn = memberRq.getEditYn();
        memberDto.socialLoginYn = memberRq.getSocialLoginYn();

        return memberDto;
    }
}
