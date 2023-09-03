package com.project.employment.member;

import com.project.employment.attach.AttachFile;
import com.project.employment.attach.AttachFileRepository;
import com.project.employment.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
        Member entity;
        List<AttachFile> attachFiles = attachFileRepo.findAllById(memberDto.getAttachFileIds());

        if (ObjectUtils.isEmpty(memberDto.getMemberId())) {
            entity = Member.create(memberDto, attachFiles);
        } else {
            entity = findById(memberDto.getMemberId());
            entity.update(memberDto, attachFiles);
        }

        repo.save(entity);
    }

//
//    private static void fileUpload(MultipartFile file, Member member) throws IOException {
//        String fileName = file.getOriginalFilename();
//        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
//
//        if (!List.of("jpg", "jpeg", "png").contains(ext)) {
//            throw new FileExtensionException("jpg, jpeg, png 형식의 파일만 업로드 가능합니다.");
//        }
//
//        byte[] bytes = file.getBytes();
//        Path path = Paths.get(member.getId() + "/upload/" + fileName);
//        Files.createDirectories(path.getParent());
//        Files.write(path, bytes);
//    }
}
