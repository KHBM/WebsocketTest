package com.foxrain.sheep.whileblack.config.auth.controller.payload.todo;

import com.foxrain.sheep.whileblack.config.auth.controller.payload.ApiResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/19 12:45 오후 19 User we at 12 45 To change this
 * template use File | Settings | File Templates.
 */
@Getter
@Setter
public class TodoPostResponse extends ApiResponse
{

  private Integer todoId;

  public TodoPostResponse(boolean success, String message, Integer todoId) {
    super(success, message);
    this.todoId = todoId;
  }

}
