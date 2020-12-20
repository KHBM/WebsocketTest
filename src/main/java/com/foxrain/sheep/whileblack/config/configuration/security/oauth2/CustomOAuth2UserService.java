package com.foxrain.sheep.whileblack.config.configuration.security.oauth2;

import com.foxrain.sheep.whileblack.config.configuration.security.UserPrincipal;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user.OAuth2UserInfo;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.user.OAuth2UserInfoFactory;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.entity.User;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.repository.UserMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:26 오후 31 User we at 23 26 To change this
 * template use File | Settings | File Templates.
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserMapper userRepository;

  @Autowired
  public CustomOAuth2UserService(UserMapper userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public OAuth2User loadUser(
      OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
        .getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("이메일 값은 필수입니다.");
    }

    Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
    User user;
    if(userOptional.isPresent()) {
      user = userOptional.get();
      if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException("이 계정은 " +
                                                              user.getProvider() + " 계정입니다. " + user.getProvider() +
                                                              " 계정으로 로그인 해 주세요.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }

  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    User user = new User();

    final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
    final AuthProvider authProvider = AuthProvider.valueOf(registrationId);

    user.setProvider(authProvider);
    user.setProviderId(oAuth2UserInfo.getId());
    user.setUserKakaoID(oAuth2UserInfo.getId());
    user.setUserName(oAuth2UserInfo.getName());
    user.setUserEmail(oAuth2UserInfo.getEmail());
    user.setUserImage(oAuth2UserInfo.getImageUrl());
    userRepository.insertUser(user);
    return user;
  }

  private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    existingUser.setUserName(oAuth2UserInfo.getName());
    existingUser.setUserImage(oAuth2UserInfo.getImageUrl());
    userRepository.updateUser(existingUser);
    return existingUser;
  }
}
