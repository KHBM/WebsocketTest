package com.foxrain.sheep.whileblack.config.configuration;

import com.foxrain.sheep.whileblack.config.auth.role.RoleType;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.CustomOAuth2UserService;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.TokenAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * Too see auto configuration in detaiil {@see OAuth2ClientAutoConfiguration} class.
 *
 * Created with intellij IDEA. by 2020 03 12/03/2020 7:28 오후 12 User we at 19 28 To change this
 * template use File | Settings | File Templates.
 */
@Configuration
@Slf4j
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class WebSecureConfiguration extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  public WebSecureConfiguration(
      CustomOAuth2UserService customOAuth2UserService,
      OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
      OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler) {
    this.customOAuth2UserService = customOAuth2UserService;
    this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }

  /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.

      이렇대.
    */
  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
//        .cors() /* 아걸 사용하려면 WebMvcConfiguration 을 적용하면 된다. */
//          .and()
//        .sessionManagement()
//          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//          .and()
        .csrf()
          .disable()
        .formLogin()
          .disable()
        .httpBasic()
          .disable()
        //.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // 401 페이지 리턴하도록 함
//          .authenticationEntryPoint(new RestAuthenticationEntryPoint()) /* 이건 위의 핸들러로 대신할 수도 있다. . */
        .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
         .and() // 이렇게 하면 401 때 루트로 가려나..
        .authorizeRequests()
        .antMatchers("/",
//                     "/calendar",
//                     "/gs-foxrain/**",
                     "/error",
                     "/favicon.ico",
                     "/**/*.png",
                     "/**/*.gif",
                     "/**/*.svg",
                     "/**/*.jpg",
                     "/**/*.html",
                     "/**/*.css",
                     "/**/*.js")
          .permitAll()
        .antMatchers("/auth/**", "/oauth2/**")
          .permitAll()
        .antMatchers("/h2-console/**",
                     "/h2-console/")
          .denyAll()
        .antMatchers("/",
            "/login/**",
//            "/*.html",
            "/user/login", "/swagger-resources/**",
                     "/swagger-ui.html",
                     "/v2/api-docs",
//                     "/h2-console/**",
//                     "/h2-console/",
                     "/webjars/**")
          .permitAll()
          .antMatchers("/console/**")
          .permitAll()
          .and()
        .authorizeRequests()
          .anyRequest().hasAuthority(RoleType.USER.getRoleName())
          .and()
        .oauth2Login()
          .authorizationEndpoint()
            .baseUri("/oauth2/authorize")
            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
            .and()
          .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")
            .and()
          .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
          .successHandler(oAuth2AuthenticationSuccessHandler)
          .failureHandler(oAuth2AuthenticationFailureHandler)
    ;

    httpSecurity.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    httpSecurity.headers().frameOptions().disable(); /* x-frame-option disable for h2 */

  }

}
