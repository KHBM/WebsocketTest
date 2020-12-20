package com.foxrain.sheep.whileblack.util.valid;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/09 12:33 오전 09 User we at 00 33 To change this
 * template use File | Settings | File Templates.
 */
public class RequestResultValidationProcessor {

//  public static ResponseEntity<ApiResponse> returnErrorResponse(Errors errors) {
//
//    // 개별 필드 오류 처리(ex: startDate에 20181322와 같은 값이 들어왔을 때)
//    List<FieldError> fieldErrors = errors.getFieldErrors();
//    if (!fieldErrors.isEmpty()) {
//      String message = fieldErrors.stream()
//          .map(e -> String.format("Property value [%s] of '%s' is invalid.", e.getRejectedValue(), e.getField()))
//          .collect(Collectors.joining(" && "));
//      return ResponseEntity.badRequest().body(new ApiResponse(false, message));
//    }
//
//    // 개별 필드 외의 오류 처리(ex: startDate와 endDate의 차이가 90일을 넘어갈 때)
//    List<ObjectError> objectErrors = errors.getAllErrors();
//    if (!objectErrors.isEmpty()) {
//      String message = objectErrors.stream()
//          .map(e -> String.format("Error in object '%s': %s", e.getObjectName(), e.getDefaultMessage()))
//          .collect(Collectors.joining(" && "));
//      return ResponseEntity.badRequest().body(new ApiResponse(false, message));
//    }
//
//    return ResponseEntity.badRequest().body(new ApiResponse(false, "입력 값을 다시 확인해 주시길 바랍니다."));
//  }

}
