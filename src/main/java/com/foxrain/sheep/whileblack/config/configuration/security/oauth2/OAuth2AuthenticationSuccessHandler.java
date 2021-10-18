package com.foxrain.sheep.whileblack.config.configuration.security.oauth2;


import static com.foxrain.sheep.whileblack.config.configuration.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.foxrain.sheep.whileblack.config.configuration.security.AppProperties;
import com.foxrain.sheep.whileblack.config.configuration.security.TokenProvider;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception.BadRequestException;
import com.foxrain.sheep.whileblack.util.cookie.CookieUtils;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:55 오후 31 User we at 23 55 To change this
 * template use File | Settings | File Templates.
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private TokenProvider tokenProvider;

  private AppProperties appProperties;

  private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, AppProperties appProperties,
                                     HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
    this.tokenProvider = tokenProvider;
    this.appProperties = appProperties;
    this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    setDefaultTargetUrl("/calendar");
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String targetUrl = determineTargetUrl(request, response, authentication);

    if (response.isCommitted()) {
      logger.debug("응답이 커밋되어 리다이렉트가 불가능합니다. 대상 주소 : " + targetUrl);
      return;
    }

    clearAuthenticationAttributes(request, response);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue);

    if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
      throw new BadRequestException("이 주소는 허가되지 않은 리다이렉트 주소입니다.");
    }

    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

    String token = tokenProvider.createToken(authentication);

    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("token", token)
        .build().toUriString();
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
  }

  private boolean isAuthorizedRedirectUri(String uri) {
    URI clientRedirectUri = URI.create(uri);

    return appProperties.getOauth2().getAuthorizedRedirectUris()
        .stream()
        .anyMatch(authorizedRedirectUri -> {
          URI authorizedURI = URI.create(authorizedRedirectUri);
          if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
              && authorizedURI.getPort() == clientRedirectUri.getPort()) {
            return true;
          }
          return false;
        });
  }

  public static void main(String[] args)
  {
    Consumer<Integer[]> sort = Arrays::sort;
    sort.accept(new Integer[]{4,2,1});
  }
}
