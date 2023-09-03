package com.project.employment.attach;

import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import lombok.Getter;

@Getter
public class AttachFileRs {
    private Long id;

    private AttachEntity attachEntity;

    private AttachType attachType;

    private String filePath;

    private String fileName;

    public static AttachFileRs from(AttachFileDto attachFileDto) {
        AttachFileRs attachFileRs = new AttachFileRs();

        attachFileRs.attachEntity = attachFileDto.getAttachEntity();
        attachFileRs.attachType = attachFileDto.getAttachType();
        attachFileRs.fileName = attachFileDto.getFileName();
        attachFileRs.filePath = attachFileDto.getFilePath();
        attachFileRs.id = attachFileDto.getId();

        return attachFileRs;
    }
}
