package com.project.employment.service;

import com.project.employment.dto.MemberSaveDto;
import com.project.employment.dto.MemberUpdateDto;
import com.project.employment.entity.Member;
import com.project.employment.exception.ConfirmPasswordMismatchException;
import com.project.employment.exception.InvalidFieldException;
import com.project.employment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    public ResponseEntity<Integer> checkLoginIdDuplicate(String loginId) {
        return new ResponseEntity<>(memberRepository.countByLoginId(loginId), HttpStatus.OK);
    }

    public void save(MemberSaveDto dto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new InvalidFieldException(bindingResult.getFieldError().getDefaultMessage());
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ConfirmPasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        try {
            Member savedMember = memberRepository.save(dto.dtoToEntity());
            // 파일 저장
            if (!dto.getFile().isEmpty()) {
                String fileName = dto.getFile().getOriginalFilename();

                byte[] bytes = dto.getFile().getBytes();
                Path path = Paths.get(savedMember.getId() + "/upload/" + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(MemberUpdateDto dto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new InvalidFieldException(bindingResult.getFieldError().getDefaultMessage());
        }

        Member member = memberRepository.findByEmail(dto.getEmail()).get();
        String date = dto.getBirthday().replace("-", "");

        member.setEditYn("Y");
        member.setMemberName(dto.getMemberName());
        member.setBirthday(LocalDate.of(
                Integer.valueOf(date.substring(0, 4)),
                Integer.valueOf(date.substring(4, 6)),
                Integer.valueOf(date.substring(6, 8))));
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setEmail(dto.getEmail());
        member.setSchoolName(dto.getSchoolName());
        member.setSocialLoginYn("Y");

        entityManager.persist(member);
    }
}
