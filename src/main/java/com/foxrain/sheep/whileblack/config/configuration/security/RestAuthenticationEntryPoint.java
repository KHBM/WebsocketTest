package com.foxrain.sheep.whileblack.config.configuration.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 *
 * 문제 생기면 401 이다.. 사용은 안한다.
 *
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:07 오후 31 User we at 23 07 To change this
 * template use File | Settings | File Templates.
 */
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    log.error("허가되지 않은 응답을 리턴합니다. 오류메시지 - {}", authException.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                       authException.getLocalizedMessage());
  }
}
