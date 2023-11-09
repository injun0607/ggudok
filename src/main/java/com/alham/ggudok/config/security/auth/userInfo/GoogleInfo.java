package com.alham.ggudok.config.security.auth.userInfo;

import java.util.Map;

public class GoogleInfo extends OAuthUserInfo{


    public GoogleInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getloginId() {
        String loginId = "go_" + (String) attributes.get("email");
        return loginId;
    }

}
