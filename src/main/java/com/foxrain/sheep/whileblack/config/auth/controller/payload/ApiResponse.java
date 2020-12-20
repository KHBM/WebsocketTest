package com.foxrain.sheep.whileblack.config.auth.controller.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * 응답의 body를 제공하기 위하여 생성합니다.
 */
@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
