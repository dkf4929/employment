package com.project.employment.attach;


import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachFileDto {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttachEntity attachEntity;

    @Enumerated(EnumType.STRING)
    private AttachType attachType;

    private String filePath;

    private String fileName;
}
