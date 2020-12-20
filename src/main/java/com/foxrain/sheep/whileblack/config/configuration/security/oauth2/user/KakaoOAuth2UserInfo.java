package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user;

import java.util.Map;

/**
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:38 오후 31 User we at 23 38 To change this
 * template use File | Settings | File Templates.
 */
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

  public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return attributes.get("id") + "";
  }

  @Override
  public String getName() {
    if (attributes.containsKey("properties")) {
      final Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
      if (properties.containsKey("nickname")) {
        return properties.get("nickname") + "";
      }
    }
    return null;
  }

  @Override
  public String getEmail() {
    if (attributes.containsKey("kakao_account")) {
      final Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");
      if (properties.containsKey("email")) {
        return properties.get("email") + "";
      }
    }
    return null;
  }

  @Override
  public String getImageUrl() {
    if (attributes.containsKey("properties")) {
      final Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
      if (properties.containsKey("thumbnail_image")) {
        return properties.get("thumbnail_image") + "";
      }
    }
    return null;
  }
}