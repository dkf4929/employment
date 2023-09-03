package com.project.employment.controller;

import com.project.employment.api.SchoolSearchAPI;
import com.project.employment.member.SchoolSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class APIController {
    private final SchoolSearchAPI schoolSearchAPI;

    @GetMapping("/school-search")
    public ResponseEntity<List<SchoolSearchDto>> getSchoolInfo(String schoolName, String schoolType) {
        return schoolSearchAPI.searchSchool(schoolName, schoolType);
    }
}
