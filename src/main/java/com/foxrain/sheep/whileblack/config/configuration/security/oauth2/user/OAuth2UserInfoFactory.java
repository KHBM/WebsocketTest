package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user;

import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.AuthProvider;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import java.util.Map;

/**
 *
 * 사용자 유저 정보 생성 팩터리
 *
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:34 오후 31 User we at 23 34 To change this
 * template use File | Settings | File Templates.
 */
public class OAuth2UserInfoFactory {

  public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
    if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
      return new GoogleOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
      return new FacebookOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
      return new GithubOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())) {
      return new KakaoOAuth2UserInfo(attributes);
    } else {
      throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
