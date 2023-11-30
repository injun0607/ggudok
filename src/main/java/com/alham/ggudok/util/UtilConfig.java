package com.alham.ggudok.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilConfig {


    public static String emailId;

    public static String emailPassword;

    @Value("${emailId}")
    public void setEmailId(String value) {
        emailId = value;
    }

    @Value("${password}")
    public void setEmailPassword(String value) {
        emailPassword = value;
    }



}
