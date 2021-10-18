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

    @GetMapping("p1")
    public String p1()
    {
        return "p1";
    }
    @GetMapping("p2")
    public String p2()
    {
        return "p2";
    }
    @GetMapping("p3")
    public String p3()
    {
        return "p3";
    }
    @GetMapping("p4")
    public String p4()
    {
        return "p4";
    }
    @GetMapping("p5")
    public String p5()
    {
        return "p5";
    }
    @GetMapping("p6")
    public String p6()
    {
        return "p6";
    }
    @GetMapping("p7")
    public String p7()
    {
        return "p7";
    }
    @GetMapping("ai3934jejf-fgasfdhdf-34934rh2-asdfa49w")
    public String a()
    {
        return "a";
    }
}
