package com.project.employment.attach;

import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attach-file")
public class AttachController {

    private final AttachFileService attachFileSvc;

    @PostMapping
    public AttachFileRs save(@RequestPart MultipartFile file, AttachType attachType) {
        return attachFileSvc.save(file, attachType);
    }
}
