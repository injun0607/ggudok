package com.alham.ggudok.config.security.auth.userInfo;

import java.util.Map;

public abstract class OAuthUserInfo {


    protected Map<String, Object> attributes;

    public OAuthUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getloginId();



}
