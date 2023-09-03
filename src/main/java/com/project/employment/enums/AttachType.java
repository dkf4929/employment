package com.project.employment.enums;

public enum AttachType {
    IMAGE("IMAGE"), // 이미지 파일
    DOCUMENT("DOCUMENT"); // 이력서, 경력기술서, 자격증 등

    private String document;

    AttachType(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }
}
