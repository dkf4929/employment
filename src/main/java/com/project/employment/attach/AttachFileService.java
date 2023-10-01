package com.project.employment.attach;

import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import com.project.employment.exception.BadRequestException;
import com.project.employment.exception.FileExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachFileService {
    private final AttachFileRepository attachFileRepo;
    private final ResourceLoader resourceLoader;

    private AttachFile findById(Long id) { return attachFileRepo.findById(id).orElseThrow(() -> {
        throw new BadRequestException();
        });
    }

    public AttachFileRs save(MultipartFile file, AttachType attachType, AttachEntity entity) {
        try {

            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if (!List.of("jpg", "jpeg", "png").contains(ext)) {
                throw new FileExtensionException("jpg, jpeg, png 형식의 파일만 업로드 가능합니다.");
            }
            String serverFileName = UUID.randomUUID().toString();
            String filePath = entity + "/" + attachType + "/" + LocalDate.now() + "/" + serverFileName + "." + ext;

            AttachFileDto attachFileDto = AttachFileDto.builder()
                    .attachEntity(entity)
                    .attachType(attachType)
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath)
                    .build();

            AttachFile savedEntity = attachFileRepo.save(AttachFile.create(attachFileDto));

            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return AttachFileRs.from(AttachFileDto.builder()
                    .attachEntity(savedEntity.getAttachEntity())
                    .attachType(savedEntity.getAttachType())
                    .fileName(savedEntity.getFileName())
                    .filePath(filePath)
                    .id(savedEntity.getId())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 업로드 도중 오류가 발생했습니다.");
        }
    }
}





