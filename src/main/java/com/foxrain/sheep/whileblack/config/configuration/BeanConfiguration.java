package com.foxrain.sheep.whileblack.config.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 *
 * If you speicify @EnableAuthorizationServer you can refer OAuth2AuthorizationServerConfiguration class for autoconfigure.
 *
 * Created with intellij IDEA. by 2020 03 26/03/2020 12:43 오전 26 User we at 00 43 To change this
 * template use File | Settings | File Templates.
 */
@Configuration
@EnableAuthorizationServer
public class BeanConfiguration {

  @Bean
  public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    return new JwtAccessTokenConverter();
  }
}
