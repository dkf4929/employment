package com.project.employment.service;

import com.project.employment.dto.MemberDto;
import com.project.employment.dto.MemberSaveDto;
import com.project.employment.dto.MemberUpdateDto;
import com.project.employment.entity.Member;
import com.project.employment.exception.ConfirmPasswordMismatchException;
import com.project.employment.exception.FileExtensionException;
import com.project.employment.exception.InvalidFieldException;
import com.project.employment.repository.MemberRepository;
import com.project.request.MemberRq;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    // 아이디 중복체크
    public ResponseEntity<Integer> checkLoginIdDuplicate(String loginId) {
        return new ResponseEntity<>(memberRepository.countByLoginId(loginId), HttpStatus.OK);
    }

    public void saveMember(MemberRq memberRq) {
        memberRq.setSocialLoginYn("N");
        MemberDto memberDto = MemberDto.from(memberRq);
        Member member = Member.create(memberDto);

        if (!ObjectUtils.isEmpty(memberRq.getFile())) {
            try {
                fileUpload(memberRq.getFile(), member);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        memberRepository.save(member);
    }

    private static void fileUpload(MultipartFile file, Member member) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();

        if (!List.of("jpg", "jpeg", "png").contains(ext)) {
            throw new FileExtensionException("jpg, jpeg, png 형식의 파일만 업로드 가능합니다.");
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(member.getId() + "/upload/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, bytes);
    }

    public void edit(MemberUpdateDto dto, BindingResult bindingResult) {
        Member member = memberRepository.findByEmail(dto.getEmail()).get();
        String date = dto.getBirthday().replace("-", "");

        try {

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
            member.setFile(dto.getFile().getBytes());
            member.setFileName(dto.getFileName());

            if (!dto.getFile().isEmpty()) {
                fileUpload(dto.getFile(), member);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
