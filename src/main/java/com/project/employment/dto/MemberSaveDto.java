package com.project.employment.dto;

import com.project.employment.entity.Member;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Data
public class MemberSaveDto {
    @NotBlank
    @Length(min = 2, max = 30)
    private String loginId;

    @NotBlank
    @Length(min = 8, max = 50, message = "비밀번호는 8자리 이상 50자리 이하여야 합니다.")
    private String password;

    @NotBlank
    @Length(min = 8, max = 50, message = "비밀번호는 8자리 이상 50자리 이하여야 합니다.")
    private String confirmPassword;

    private MultipartFile file;

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

    public Member dtoToEntity() throws IOException {
        String date = this.birthday.replace("-", "");

        return Member.builder()
                .birthday(LocalDate.of(
                        Integer.valueOf(date.substring(0, 4)),
                        Integer.valueOf(date.substring(4, 6)),
                        Integer.valueOf(date.substring(6, 8))))
                .memberName(this.memberName)
                .password(new BCryptPasswordEncoder().encode(this.password))
                .role(new String[]{"ROLE_USER"})
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .loginId(this.loginId)
                .schoolName(this.schoolName)
                .file(this.file.getBytes())
                .socialLoginYn("N")
                .build();
    }

}
