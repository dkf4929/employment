package com.project.employment.service;

import com.project.employment.dto.MemberDto;
import com.project.employment.dto.MemberImageFileDto;
import com.project.employment.dto.MemberUpdateRq;
import com.project.employment.entity.Member;
import com.project.employment.entity.MemberImageFile;
import com.project.employment.exception.FileExtensionException;
import com.project.employment.repository.MemberRepository;
import com.project.request.MemberRq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 회원입니다.");});
    }

    // 아이디 중복체크
    public ResponseEntity<Integer> checkLoginIdDuplicate(String loginId) {
        return new ResponseEntity<>(memberRepository.countByLoginId(loginId), HttpStatus.OK);
    }

    public void saveMember(MemberRq memberRq) {
        memberRq.setSocialLoginYn("N");
        MemberDto memberDto = MemberDto.from(memberRq);
        try {
            MemberImageFileDto memberImageFileDto = MemberImageFileDto.from(memberRq.getFile());
            MemberImageFile memberImageFile = MemberImageFile.create(memberRq.getFile());

            Member member = Member.create(memberDto, memberImageFile);

            if (!ObjectUtils.isEmpty(memberRq.getFile())) {
                fileUpload(memberRq.getFile(), member);
            }

            memberRepository.save(member);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(MemberUpdateRq rq, BindingResult bindingResult) {
        Member member = findById(rq.getMemberId());
        try {

            member.update(rq, MemberImageFile.create(rq.getFile()));
            member.setSocialLoginYn("Y");

            if (!ObjectUtils.isEmpty(rq.getFile())) {
                fileUpload(rq.getFile(), member);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
