package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user;

import java.util.Map;

/**
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:30 오후 31 User we at 23 30 To change this
 * template use File | Settings | File Templates.
 */
public abstract class OAuth2UserInfo {
  protected Map<String, Object> attributes;

  public OAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public abstract String getId();

  public abstract String getName();

  public abstract String getEmail();

  public abstract String getImageUrl();
}
