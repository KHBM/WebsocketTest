package com.foxrain.sheep.whileblack.logic.funrun.controller;

import com.foxrain.sheep.whileblack.config.configuration.security.CurrentUser;
import com.foxrain.sheep.whileblack.config.configuration.security.UserPrincipal;
import com.foxrain.sheep.whileblack.logic.funrun.domain.Answer;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with intellij IDEA. 
 * by 2021 01 2021/01/03 7:16 오후 03
 * User we at 19 16
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Slf4j
@RestController
public class CheckController
{
    private Map<String, Boolean> m1 = Maps.newConcurrentMap();
    private Map<String, Boolean> m2 = Maps.newConcurrentMap();
    private Map<String, Boolean> m3 = Maps.newConcurrentMap();
    private Map<String, Boolean> m4 = Maps.newConcurrentMap();
    private Map<String, Boolean> m5 = Maps.newConcurrentMap();
    private Map<String, Boolean> m6 = Maps.newConcurrentMap();
    private final Map<String, Boolean> m7 = Maps.newConcurrentMap();
    private final AtomicBoolean isFisrtFound = new AtomicBoolean(false);
    private final Random random = new Random(32);
    private final Map<String, String> codes = Maps.newConcurrentMap();

    @PostMapping(value = "p1check", produces = "text/plain")
    public String p1(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());

        if (answer.getValue().equalsIgnoreCase("1991-06-10"))
        {
            m1.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            return "p2";
        }
        else
        {
            m1.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }

    }
    @PostMapping("p2check")
    public String p2(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());

        if (answer.getValue().equalsIgnoreCase("2019-05-30"))
        {
            m2.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            return "p3";
        }
        else
        {
            m2.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }
    }
    @PostMapping("p3check")
    public String p3(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());

        if (answer.getValue().equals("피카츄"))
        {
            m3.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            return "p4";
        }
        else
        {
            m3.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }
    }
    @PostMapping("p4check")
    public String p4(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());

        if (answer.getValue().equals("43"))
        {
            m4.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            return "p5";
        }
        else
        {
            m4.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }
    }
    @PostMapping("p5check")
    public String p5(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());

        if (answer.getValue().equalsIgnoreCase("가수의실력이신데요"))
        {
            m5.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            return "p7";
        }
        else
        {
            m5.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }
    }

    @PostMapping("p6check")
    public String p6(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());
        log.info("User final : {}", userPrincipal);

        if (answer.getValue().equalsIgnoreCase("5f6z5i4e4h43468a-94383d-gv422c"))
        {
            m6.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            if (checkAll(userPrincipal.getName()))
            {
                log.info("user {} got into place!", userPrincipal);
                return "ai3934jejf-fgasfdhdf-34934rh2-asdfa49w";
            }
            else
                return "null";

        }
        else
        {
            m6.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }
    }

    @PostMapping("p7check")
    public String p7(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());

        if (answer.getValue().equalsIgnoreCase("(유)에이스톰"))
        {
            m7.put(userPrincipal.getName(), true);
            printMapStatus(userPrincipal.getName());
            return "calendar";
        }
        else
        {
            m7.put(userPrincipal.getName(), false);
            printMapStatus(userPrincipal.getName());
            return "false";
        }
    }

    @PostMapping("acheck")
    public String a(@RequestBody Answer answer, @CurrentUser UserPrincipal userPrincipal)
    {
        log.info("Answer : {}, {}", answer.getValue(), userPrincipal.getName());
        log.info("User final : {}", userPrincipal);

        if (!checkAll(userPrincipal.getName()))
        {
            return "문제가 풀리지 않았어요.";
        }

        if (codes.containsKey(userPrincipal.getName()))
        {
            return codes.get(userPrincipal.getName());
        }

        if (!isFisrtFound.get())
        {
            String firstcode = "Code 9320-0001 를 전달하세요";
            log.info("user {} got into place! is first {}", userPrincipal, firstcode);
            isFisrtFound.set(true);
            codes.put(userPrincipal.getName(), firstcode);
        }
        else
        {
            String subCode = String.format("Code 값 Rh3-%d423%d를 전달하세요"
                , random.nextInt(43403), random.nextInt(43403));
            log.info("user {} got into place! not first {}", userPrincipal, subCode);
            codes.put(userPrincipal.getName(), subCode);
        }

        return codes.get(userPrincipal.getName());
    }

    private boolean checkAll(String name)
    {
        return checkEach(m1.get(name)) &&
            checkEach(m2.get(name)) &&
          checkEach(m3.get(name)) &&
            checkEach(m4.get(name)) &&
          checkEach(m5.get(name)) &&
        checkEach(m6.get(name)) &&
            checkEach(m7.get(name));
    }

    public boolean checkEach(Boolean bool)
    {
        return bool == null ? false : bool;
    }

    public void printMapStatus(String name)
    {
        log.info("1 : {}={}", name, checkEach(m1.get(name)));
        log.info("2 : {}={}", name, checkEach(m2.get(name)));
        log.info("3 : {}={}", name, checkEach(m3.get(name)));
        log.info("4 : {}={}", name, checkEach(m4.get(name)));
        log.info("5 : {}={}", name, checkEach(m5.get(name)));
        log.info("6 : {}={}", name, checkEach(m6.get(name)));
        log.info("6 : {}={}", name, checkEach(m7.get(name)));
    }
}
