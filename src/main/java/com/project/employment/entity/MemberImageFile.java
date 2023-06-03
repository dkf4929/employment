package com.project.employment.entity;

import com.project.employment.dto.MemberImageFileDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Getter
@Table(name = "member_image_file")
public class MemberImageFile {
    @Id
    @GeneratedValue
    @Column(name = "member_image_file_id")
    private Long id;

    @Lob
    private byte[] file;

    private String fileName;

    public static MemberImageFile create(MultipartFile file) throws IOException {
        MemberImageFile entity = new MemberImageFile();

        entity.file = file.getBytes();
        entity.fileName = file.getOriginalFilename();

        return entity;
    }
}
