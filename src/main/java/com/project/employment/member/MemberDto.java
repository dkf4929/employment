package com.project.employment.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    private List<Long> attachFileIds;

    private String fileName;

    private String editYn;

    @Setter
    private String socialLoginYn;

    public static MemberDto from(MemberInsertRq memberInsertRq) {
        MemberDto memberDto = new MemberDto();

        memberDto.memberId = memberInsertRq.getMemberId();
        memberDto.loginId = memberInsertRq.getLoginId();
        memberDto.password = new BCryptPasswordEncoder().encode(memberInsertRq.getPassword());
        memberDto.email = memberInsertRq.getEmail();
        memberDto.memberName = memberInsertRq.getMemberName();
        memberDto.birthday = memberInsertRq.getBirthday();
        memberDto.phoneNumber = memberInsertRq.getPhoneNumber();
        memberDto.schoolName = memberInsertRq.getSchoolName();
        memberDto.editYn = memberInsertRq.getEditYn();
        memberDto.socialLoginYn = memberInsertRq.getSocialLoginYn();
        memberDto.attachFileIds = memberInsertRq.getAttachFileId();

        return memberDto;
    }

    public static MemberDto from(MemberUpdateRq memberUpdateRq) {
        MemberDto memberDto = new MemberDto();

        memberDto.memberId = memberUpdateRq.getMemberId();
        memberDto.memberName = memberUpdateRq.getMemberName();
        memberDto.attachFileIds = memberUpdateRq.getAttachFileId();
        memberDto.birthday = memberUpdateRq.getBirthday();
        memberDto.phoneNumber = memberUpdateRq.getPhoneNumber();
        memberDto.schoolName = memberUpdateRq.getPhoneNumber();

        return memberDto;
    }
}
