package com.foxrain.sheep.whileblack.config.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * Cors 필터 대신 이걸로 편하게 설정이 가능하지만, 생성하고 사용하진 않는다.. 왜냐고 묻는다면..
 *
 * Created with intellij IDEA. by 2020 04 2020/04/01 1:17 오전 01 User we at 01 17 To change this
 * template use File | Settings | File Templates.
 */
//@EnableWebMvc
//@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  private final long MAX_AGE_SECS = 3600;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
//        .addMapping("/**")
        .addMapping("/**")
//        .allowedOrigins("*")
        .allowedOrigins("121.130.167.189")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(MAX_AGE_SECS);
  }


}
