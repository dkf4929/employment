package com.project.employment.dto;

import com.project.employment.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Data
public class MemberUpdateDto {
    @NotBlank
    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]+$|^[가-힣\\s]+$", message = "성명은 한글 또는 영어만 입력 가능합니다.")
    private String memberName;

    @NotBlank
    private String birthday;

    @NotBlank
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String email;

    @NotBlank
    @Pattern(regexp="^01([016789])?[1-9]\\d{2,3}\\d{4}$", message="휴대폰 번호 형식에 맞게 입력해주세요.")
    private String phoneNumber;

    @NotBlank
    private String schoolName;

    public Member dtoToEntity() {
        String date = this.birthday.replace("-", "");

        return Member.builder()
                .birthday(LocalDate.of(
                        Integer.valueOf(date.substring(0, 4)),
                        Integer.valueOf(date.substring(4, 6)),
                        Integer.valueOf(date.substring(6, 8))))
                .memberName(this.memberName)
                .role(new String[]{"ROLE_USER"})
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .schoolName(this.schoolName)
                .build();
    }
}
