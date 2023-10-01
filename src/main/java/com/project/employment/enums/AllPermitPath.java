package com.project.employment.enums;

public enum AllPermitPath {
    RESOURCE_PATH("/resources/*"),
    START_PATH("/"),
    EXTERNAL_API_PATH("/api/**"),
    USER_IMAGE_PATH("/image/**"),
    LOGIN_PATH("/login/**"),
    FAVICON_PATH("/favicon.ico"),
    ID_CHECK_PATH("/member/id-check"),
    SCHOOL_SEARCH_PATH("/member/school-search"),
    USER_IMAGE_UPLOAD_PATH("/upload/**"),
    JS_PATH("/js/**"),
    CSS_PATH("/css/**"),
    MEMBER_SAVE_PATH("/member/add"),
    REGISTER_PATH("/register"),
    COMPANY_SAVE_PATH("/company/add"),
    ADD_FILE_PATH("/attach/**");


    private final String path;

    AllPermitPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
