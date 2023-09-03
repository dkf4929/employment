package com.project.employment.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SchoolSearchDto {
    private String name;
    private String campusGubun;
    private String address;
    private String gubun;
}
