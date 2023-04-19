package com.project.employment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Member implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String password;

    private String email;

    private String memberName;

    private LocalDate birthday;

    private String phoneNumber;

    private String schoolName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String email, String memberName, LocalDate birthday, String phoneNumber, String schoolName, String ... role) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.memberName = memberName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.schoolName = schoolName;

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
