package com.foxrain.sheep.whileblack.config.configuration.security.oauth2;

import static com.foxrain.sheep.whileblack.config.configuration.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.foxrain.sheep.whileblack.util.cookie.CookieUtils;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/01 1:08 오전 01 User we at 01 08 To change this
 * template use File | Settings | File Templates.
 */
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

  public OAuth2AuthenticationFailureHandler(
      HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
    this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue)
        .orElse(("/"));

    targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("error", URLEncoder.encode(exception.getLocalizedMessage(), "UTF-8"))
        .build().toUriString();

    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}
