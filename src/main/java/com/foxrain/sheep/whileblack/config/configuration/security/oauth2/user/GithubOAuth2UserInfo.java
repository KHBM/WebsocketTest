package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user;

import java.util.Map;

/**
 *
 * 깃헙에서 사용자 정보 가져오기.
 *
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:36 오후 31 User we at 23 36 To change this
 * template use File | Settings | File Templates.
 */
public class GithubOAuth2UserInfo extends OAuth2UserInfo {

  public GithubOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return ((Integer) attributes.get("id")).toString();
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
    return (String) attributes.get("avatar_url");
  }
}
