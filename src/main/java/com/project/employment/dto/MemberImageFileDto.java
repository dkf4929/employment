package com.project.employment.dto;

import com.project.employment.entity.Member;
import com.project.employment.entity.MemberImageFile;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
public class MemberImageFileDto {
    @Lob
    private byte[] file;

    private String fileName;

    public static MemberImageFileDto from(MultipartFile multipartFile) {
        MemberImageFileDto memberImageFileDto = new MemberImageFileDto();

        try {
            memberImageFileDto.file = multipartFile.getBytes();
            memberImageFileDto.fileName = multipartFile.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return memberImageFileDto;
    }
}
