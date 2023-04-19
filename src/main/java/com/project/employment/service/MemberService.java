package com.project.employment.service;

import com.project.employment.dto.MemberSaveDto;
import com.project.employment.entity.Member;
import com.project.employment.exception.ConfirmPasswordMismatchException;
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

    public void save(MemberSaveDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ConfirmPasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.save(dto.dtoToEntity());
    }
}
