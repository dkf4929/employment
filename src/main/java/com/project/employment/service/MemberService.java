package com.project.employment.service;

import com.project.employment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseEntity<Integer> checkLoginIdDuplicate(String loginId) {
        return new ResponseEntity<>(memberRepository.countByLoginId(loginId), HttpStatus.OK);
    }
}
