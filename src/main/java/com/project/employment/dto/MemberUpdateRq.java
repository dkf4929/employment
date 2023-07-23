package com.project.employment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberUpdateRq {
    private Long memberId;

    @NotBlank(message = "이름을 입력하세요.")
    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]+$|^[가-힣\\s]+$", message = "성명은 한글 또는 영어만 입력 가능합니다.")
    private String memberName;

    @NotBlank(message = "생일을 입력하세요.")
    private String birthday;

    @NotBlank(message = "이메일 주소를 입력하세요")
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String email;

    @NotBlank(message = "휴대폰번호를 입력하세요.")
    @Pattern(regexp="^01([016789])?[1-9]\\d{2,3}\\d{4}$", message="휴대폰 번호 형식에 맞게 입력해주세요.")
    private String phoneNumber;

    private MultipartFile file;

    @NotBlank(message = "최종학력을 입력하세요.")
    private String schoolName;
}
