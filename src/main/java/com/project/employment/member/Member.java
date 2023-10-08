package com.project.employment.member;

import com.project.employment.attach.AttachFile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "loginId", name = "uk_login_id"),
                @UniqueConstraint(columnNames = "email", name = "uk_email")})
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String email;

    private String memberName;

    private LocalDate birthday;

    private String phoneNumber;

    private String schoolName;

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    @Where(clause = "attach_entity = 'MEMBER'")
    private List<AttachFile> attachFile;

    private String editYn; // 구글 로그인 시 프로필에 정보를 입력했는지 여부.

    private String socialLoginYn;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @Builder
    public Member(String loginId, String password, String email, String memberName, LocalDate birthday, String phoneNumber, String schoolName, String editYn, String socialLoginYn, String ... role) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.memberName = memberName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.schoolName = schoolName;
        this.editYn = editYn;
        this.socialLoginYn = socialLoginYn;

        for (String s : role) {
            roles.add(s);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Member create(MemberDto memberDto, List<AttachFile> attachFile) {
        Member member = new Member();

        member.id = memberDto.getMemberId();
        member.loginId = memberDto.getLoginId();
        member.password = memberDto.getPassword();
        member.email = memberDto.getEmail();
        member.memberName = memberDto.getMemberName();
        String birthday = memberDto.getBirthday().replace("-", "");
        member.birthday = LocalDate.of(
                Integer.valueOf(birthday.substring(0, 4)),
                Integer.valueOf(birthday.substring(4, 6)),
                Integer.valueOf(birthday.substring(6, 8)));
        member.phoneNumber = memberDto.getPhoneNumber();
        member.schoolName = memberDto.getSchoolName();
        member.attachFile = attachFile;
        member.editYn = memberDto.getEditYn();
        member.socialLoginYn = memberDto.getSocialLoginYn();
        if (!memberDto.getMemberName().equals("admin")) {
            member.roles.add("ROLE_USER");
        } else {
            member.roles.add("ROLE_ADMIN");
        }

        return member;
    }

    public void update(MemberDto dto, List<AttachFile> attachFile) {
        this.memberName = dto.getMemberName();
        this.birthday = LocalDate.of(
                Integer.valueOf(dto.getBirthday().replace("-", "").substring(0, 4)),
                Integer.valueOf(dto.getBirthday().replace("-", "").substring(4, 6)),
                Integer.valueOf(dto.getBirthday().replace("-", "").substring(6, 8)));
        this.phoneNumber = dto.getPhoneNumber();
        this.attachFile = attachFile;
        this.schoolName = dto.getSchoolName();
        this.attachFile = attachFile;
    }
}
