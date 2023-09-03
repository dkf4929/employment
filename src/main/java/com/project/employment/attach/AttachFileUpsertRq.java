package com.project.employment.attach;

import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachFileUpsertRq {
    private Long id;

    private AttachEntity attachEntity;

    private AttachType attachType;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileName;
}
