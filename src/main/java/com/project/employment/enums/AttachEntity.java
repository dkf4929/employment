package com.project.employment.enums;

public enum AttachEntity {
    MEMBER("MEMBER");

    private String type;

    AttachEntity(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
