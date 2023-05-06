package com.project.employment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    private String email;

    private String memberName;

    private LocalDate birthday;

    private String phoneNumber;

    private String schoolName;

    @Lob
    private byte[] file;

    private String fileName;

    private String editYn; // 구글 로그인 시 프로필에 정보를 입력했는지 여부.

    private String socialLoginYn;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @Builder
    public Member(String loginId, String password, String email, String memberName, LocalDate birthday, String phoneNumber, String schoolName, String editYn, String socialLoginYn, byte[] file, String fileName, String ... role) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.memberName = memberName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.schoolName = schoolName;
        this.editYn = editYn;
        this.socialLoginYn = socialLoginYn;
        this.fileName = fileName;
        this.file = file;

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

}
