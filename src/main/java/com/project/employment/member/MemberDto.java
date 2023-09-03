package com.project.employment.member;

import com.project.employment.attach.AttachFileDto;
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

    public static MemberDto from(MemberUpsertRq memberUpsertRq) {
        MemberDto memberDto = new MemberDto();

        memberDto.memberId = memberUpsertRq.getMemberId();
        memberDto.loginId = memberUpsertRq.getLoginId();
        memberDto.password = new BCryptPasswordEncoder().encode(memberUpsertRq.getPassword());
        memberDto.email = memberUpsertRq.getEmail();
        memberDto.memberName = memberUpsertRq.getMemberName();
        memberDto.birthday = memberUpsertRq.getBirthday();
        memberDto.phoneNumber = memberUpsertRq.getPhoneNumber();
        memberDto.schoolName = memberUpsertRq.getSchoolName();
        memberDto.editYn = memberUpsertRq.getEditYn();
        memberDto.socialLoginYn = memberUpsertRq.getSocialLoginYn();
        memberDto.attachFileIds = memberUpsertRq.getAttachFileIds();

        return memberDto;
    }
}
