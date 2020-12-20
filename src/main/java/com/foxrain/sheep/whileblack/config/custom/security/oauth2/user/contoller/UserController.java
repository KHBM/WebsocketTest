package com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.contoller;

import com.foxrain.sheep.whileblack.config.configuration.security.CurrentUser;
import com.foxrain.sheep.whileblack.config.configuration.security.UserPrincipal;
import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception.ResourceNotFoundException;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.entity.User;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with intellij IDEA.
 * by 2020 12 2020/12/19 3:56 오후 19
 * User we at 15 56
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@RestController
@RequestMapping(value="/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("사용자 Social 로그인 API ")
    @ApiParam(value = "type : 로그인 타입, kakao only supported.")
    @GetMapping(value = "/login", produces = "text/html")
    public ResponseEntity<String> loginUser(@RequestParam(required = false, name = "type", defaultValue = "default")String param) {

        if (param.equalsIgnoreCase("kakao")) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/oauth2/authorization/kakao"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("THE " + param + " login is not supported.");
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

}
