package com.project.employment.attach;

import com.project.employment.enums.AttachEntity;
import com.project.employment.enums.AttachType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachController {

    private final AttachFileService attachFileSvc;

    @ResponseBody
    @PostMapping("/attachType/{attachType}/entity/{entity}")
    public AttachFileRs save(@RequestPart MultipartFile file, AttachType attachType, AttachEntity attachEntity) {
        return attachFileSvc.save(file, attachType, attachEntity);
    }

    @GetMapping
    public String attachForm(Model model, AttachType attachType, AttachEntity attachEntity) {
        model.addAttribute("attachType", attachType);
        model.addAttribute("attachEntity", attachEntity);
        return "/popup/attach";
    }

    @GetMapping("/view")
    @ResponseBody
    public void viewImage(String filePath, HttpServletResponse response) throws IOException {
        // 파일 시스템 경로를 사용하여 리소스를 생성
        Resource resource = new FileSystemResource(filePath);
        if (resource.exists()) {
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);

            // 이미지로 응답 설정
            response.setContentType("image/png");
            response.getOutputStream().write(bytes);
        } else {
            // 파일이 존재하지 않을 경우에 대한 처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
