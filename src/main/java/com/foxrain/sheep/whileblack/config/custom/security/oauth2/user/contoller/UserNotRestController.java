package com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with intellij IDEA. by 2020 12 2020/12/20 1:31 오후 20 User we at 13 31 To change this template use File |
 * Settings | File Templates.
 *
 * @author foxrain
 */
@Controller
public class UserNotRestController
{
    @GetMapping("calendar")
    public String calendar()
    {
        return "calendar";
    }
}
