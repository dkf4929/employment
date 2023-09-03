package com.project.employment.login.oauth2;

import com.project.employment.member.Member;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String loginId;
    private String email;

    public SessionUser(Member member) {
        this.loginId = member.getLoginId();
        this.email = member.getEmail();
    }
}
