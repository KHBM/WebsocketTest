package com.foxrain.sheep.whileblack.config.configuration.cors;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created with intellij IDEA. by 2020 03 26/03/2020 8:05 오후 26 User we at 20 05 To change this
 * template use File | Settings | File Templates.
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    HttpServletRequest request = (HttpServletRequest) req;

    log.debug("CorsFilter filter");

    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-AUTH-TOKEN");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Access-Control-Allow-Credentials", "true");

    if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
      chain.doFilter(req, res);
    }
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
  }

}
