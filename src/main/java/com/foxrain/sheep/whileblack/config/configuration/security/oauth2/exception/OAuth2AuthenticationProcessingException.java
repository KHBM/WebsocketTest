package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * 인증 처리 중 에러 발생 시 던질 예외 객체.
 *
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:33 오후 31 User we at 23 33 To change this
 * template use File | Settings | File Templates.
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
  public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
    super(msg, t);
  }

  public OAuth2AuthenticationProcessingException(String msg) {
    super(msg);
  }
}
