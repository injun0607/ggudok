package com.alham.ggudok.exception.member;

import lombok.Data;

@Data
public class ErrorMember {

    private String message;

    public ErrorMember(String message) {
        this.message = message;
    }
}
