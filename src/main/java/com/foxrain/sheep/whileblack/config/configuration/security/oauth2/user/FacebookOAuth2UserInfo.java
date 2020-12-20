package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user;

import java.util.Map;

/**
 *
 * 페북에서 사용자 정보 가져오기..
 *
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:37 오후 31 User we at 23 37 To change this
 * template use File | Settings | File Templates.
 */
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
  public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return (String) attributes.get("id");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    if(attributes.containsKey("picture")) {
      Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
      if(pictureObj.containsKey("data")) {
        Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
        if(dataObj.containsKey("url")) {
          return (String) dataObj.get("url");
        }
      }
    }
    return null;
  }
}
