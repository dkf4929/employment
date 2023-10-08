package com.project.employment.member;

import com.project.employment.attach.AttachFile;
import com.project.employment.attach.AttachFileRepository;
import com.project.employment.exception.BadRequestException;
import com.project.employment.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.naming.AuthenticationException;
import javax.security.auth.login.LoginException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository repo;
    private final AttachFileRepository attachFileRepo;
//
    public Member findById(Long memberId) {
        return repo.findById(memberId).orElseThrow(() -> {
            throw new BadRequestException();});
    }

    // 아이디 중복체크
    public ResponseEntity<Integer> checkLoginIdDuplicate(String loginId) {
        return new ResponseEntity<>(repo.countByLoginId(loginId), HttpStatus.OK);
    }

    public void save(MemberDto memberDto) {
        memberDto.setSocialLoginYn("N");
        List<AttachFile> attachFiles = attachFileRepo.findAllById(memberDto.getAttachFileIds());

        Member entity = Member.create(memberDto, attachFiles);

        attachFiles.stream().forEach(attachFile -> {
            memberDto.getAttachFileIds().stream().forEach(sn -> {
                attachFile.updateRelatedEntity(sn);
            });
        });

        try {
            repo.save(entity);
        } catch (Exception e) {
            throw new BusinessException("저장 중 문제가 발생하였습니다.");
        }
    }

    public void edit(MemberDto dto) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = findById(dto.getMemberId());

        if (loginMember.getId() != member.getId()) throw new BusinessException("잘못된 경로로 접근하였습니다.");

        List<AttachFile> savedFileList = member.getAttachFile();
        List<AttachFile> attachFileList = attachFileRepo.findAllById(dto.getAttachFileIds());

        if (!attachFileList.containsAll(savedFileList)) { // 파일이 수정된 경우 기존 엔터티 삭제
            attachFileRepo.deleteAll(savedFileList);
            member.getAttachFile().clear();

            // 파일 엔터티 업데이트
            attachFileList.stream().forEach(attachFile -> {
                attachFile.updateRelatedEntity(member.getId());
            });
        }

        // 파일 엔터티 업데이트
        attachFileList.stream().forEach(attachFile -> {
            attachFile.updateRelatedEntity(member.getId());
        });

        member.update(dto, attachFileList);
    }
}
