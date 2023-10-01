package com.project.employment.attach;

import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AttachFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_file_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttachEntity attachEntity;

    @Column(name = "entity_id")
    private Long entity;

    @Enumerated(EnumType.STRING)
    private AttachType attachType;

    private String filePath;

    private String fileName;

    public static AttachFile create(AttachFileDto dto) {
        AttachFile attachFile = new AttachFile();

        attachFile.attachEntity = dto.getAttachEntity();
        attachFile.attachType = dto.getAttachType();
        attachFile.fileName = dto.getFileName();
        attachFile.filePath = dto.getFilePath();
        attachFile.id = dto.getId();

        return attachFile;
    }
}
