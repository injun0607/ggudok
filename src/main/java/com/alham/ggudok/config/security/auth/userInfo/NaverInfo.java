package com.alham.ggudok.config.security.auth.userInfo;

import java.util.Map;

public class NaverInfo extends OAuthUserInfo{
    public NaverInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getloginId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        String loginId = "na_"+(String) response.get("email");

        return loginId;
    }


}
