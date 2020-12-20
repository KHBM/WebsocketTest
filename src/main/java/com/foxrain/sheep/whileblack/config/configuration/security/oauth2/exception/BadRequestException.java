package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/01 1:10 오전 01 User we at 01 10 To change this
 * template use File | Settings | File Templates.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
