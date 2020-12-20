package com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/01 1:11 오전 01 User we at 01 11 To change this
 * template use File | Settings | File Templates.
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  private String resourceName;
  private String fieldName;
  private Object fieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
}
