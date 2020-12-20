package com.foxrain.sheep.whileblack.config.configuration.security;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with intellij IDEA. by 2020 03 2020/03/31 11:56 오후 31 User we at 23 56 To change this
 * template use File | Settings | File Templates.
 */
@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private final Auth auth = new Auth();
  private final OAuth2 oauth2 = new OAuth2();

  public static class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;

    public String getTokenSecret() {
      return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.tokenSecret = tokenSecret;
    }

    public long getTokenExpirationMsec() {
      return tokenExpirationMsec;
    }

    public void setTokenExpirationMsec(long tokenExpirationMsec) {
      this.tokenExpirationMsec = tokenExpirationMsec;
    }
  }

  public static final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;
      return this;
    }
  }
}
