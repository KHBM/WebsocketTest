package com.foxrain.sheep.whileblack.websocket.controller;

import com.foxrain.sheep.whileblack.config.configuration.security.UserPrincipal;
import com.foxrain.sheep.whileblack.logic.main.Board;
import com.foxrain.sheep.whileblack.logic.main.Initializer;
import com.foxrain.sheep.whileblack.logic.manage.UserManager;
import com.foxrain.sheep.whileblack.websocket.message.from.SelectId;
import com.foxrain.sheep.whileblack.websocket.message.to.Broadcast;
import com.foxrain.sheep.whileblack.websocket.message.to.MapInfo;
import com.google.common.collect.Sets;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/20 2:38 오후 20
 * User we at 14 38
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Slf4j(topic = "con")
@Controller
public class GreetingController {

//    private Map<String, Board> userBoard;
//    private Map<String, Integer> userLevel;
    private final OAuth2AuthorizedClientService authService;
    private final SimpMessageSendingOperations messagingTemplate;
//    private final BoardLevelService service;
    private final UserManager userManager;
    private final Set<OAuth2AuthenticationToken> sessions;

    @Autowired
    public GreetingController(OAuth2AuthorizedClientService authService
        , SimpMessageSendingOperations messagingTemplate
        , UserManager userManager)
    {
        this.authService = authService;
        this.messagingTemplate = messagingTemplate;
        this.userManager = userManager;
        this.sessions = Sets.newConcurrentHashSet();
    }

    @PostConstruct
    public void inits()
    {
    }

    @EventListener
    public void connectEventListener(SessionConnectedEvent event)
    {
        log.info("Session connected : {}", event.getUser().getName());
        sessions.add((OAuth2AuthenticationToken)event.getUser());
//        for (String name : sessions)
//            {
//                final Broadcast broadcast = new Broadcast();
//                broadcast.setId(name);
//                broadcast.setMapInfo(userManager.getCurrentBoardOfUser(name).toArray());
//                messagingTemplate.convertAndSend("/queue/notify", broadcast);
//        }

    }

    @EventListener
    public void disconnectEventListener(SessionDisconnectEvent event)
    {
        log.info("Session disconnected : {}", event.getUser().getName());
        final Broadcast broadcast = new Broadcast();
        broadcast.setId(event.getUser().getName());
        broadcast.setMapInfo(new int[0][]);
        messagingTemplate.convertAndSend("/queue/notify", broadcast);
        sessions.remove(event.getUser());
    }

//    @MessageMapping("/hello")
    @MessageMapping("/hello")
//    @SendTo("/ftopic/greetings")
//    @SendToUser("/fqueue/greetings")
    @SendToUser("/queue/greetings")
//    @SendToUser("/user/greetings")
    public MapInfo greeting(@Payload SelectId message
//        , @CurrentUser UserPrincipal userPrincipal
//        , /*@DestinationVariable("userId")String userId, */OAuth2AuthenticationToken authentication
        , Principal principal
    ) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getId()+"") + "!");
//        log.info("UserId : : {}", userId);
//        log.info("Authentication : {}", authentication);
//        log.info("userP : {}", userPrincipal);
        log.info("Java principal : {}/{}", principal, principal.getName());
//        final OAuth2AuthorizedClient oAuth2AuthorizedClient = authService
//            .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
//        log.info("oauth info : {}", oAuth2AuthorizedClient);
//        final String principalName = oAuth2AuthorizedClient.getPrincipalName();
        final String principalName = principal.getName();
        log.info("NAme : {}", principalName);
//        if (!userBoard.containsKey(principalName))
//        {
//            final Board nextLevel = service.getNextLevel(getUserLevel(principalName));
//            replaceUserMap(principalName, nextLevel);
//        }
//        final int[][] array = getUserMap(principalName).toArray();
        final Board currentBoard = userManager.getCurrentBoardOfUser(principalName);
        final int[][] array = currentBoard.toArray();
        log.info("array : {}", array);
//        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/greetings", new MapInfo(array));
        broadcastMapInfo();
        return new MapInfo(array, false, "");
    }

//    @MessageMapping("/click")
    @MessageMapping("/click")
//    @SendTo("/ftopic/greetings")
//    @SendToUser("/fqueue/greetings")
    @SendToUser("/queue/greetings")
//    @SendToUser("/user/greetings")
    public MapInfo click(@Payload SelectId message
//        , @CurrentUser UserPrincipal userPrincipal
//        , OAuth2AuthenticationToken authentication
//        , SimpMessageHeaderAccessor accessor
        , Principal principal
    ) throws Exception {
//        log.info("Got message from : {}/{}", message, userPrincipal.getName());
//        log.info("userP : {}", userPrincipal);
//        final OAuth2AuthorizedClient oAuth2AuthorizedClient = authService
//            .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
//        log.info("user prin: {}", oAuth2AuthorizedClient.getPrincipalName());
//        final Board prev = getUserMap(oAuth2AuthorizedClient.getPrincipalName());
        if (message.getId() < 0)
        {
            userManager.initialzeUserMap(principal.getName());
        }
        log.info("user prin: {}", principal.getName());
//        final Board prev = getUserMap(principal.getName());
        final Board prev = userManager.getCurrentBoardOfUser(principal.getName());
        prev.select(message.getId());
        Initializer.debugMap(prev);
        Initializer.createQuery(prev);
        final Broadcast broadcast = new Broadcast();
        broadcast.setId(principal.getName());
        broadcast.setMapInfo(prev.toArray());
        broadcast.setName(getUserNicknameMap((OAuth2AuthenticationToken) principal).get("nickname"));
        messagingTemplate.convertAndSend("/queue/notify", broadcast);
        return new MapInfo(prev.toArray(), prev.isClear(), "");
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }

//    private Board getUserMap(String user)
//    {
//        if (!userBoard.containsKey(user))
//        {
//            log.info("New arrya return");
//            userBoard.put(user, init(new String[][]{{"1", "1", "0", "1", "1"},
//                {"1", "1", "0", "1", "1"}}, new NineByNineRule()));
//        }
//        return userBoard.get(user);
//    }
//
//    private void setUserLevel(String user, int level)
//    {
//        userLevel.put(user, level);
//    }
//
//    private int getUserLevel(String user)
//    {
//        return userLevel.getOrDefault(user, 0);
//    }
//
//    private void replaceUserMap(String user, Board board)
//    {
//        userBoard.put(user, board);
//    }

    @MessageMapping("/cleard")
    @SendToUser("/queue/greetings")
    public MapInfo isclear(@Payload String message
        , Principal principal
    ) throws Exception {
//        final Board userMap = getUserMap(principal.getName());
        final Board userMap = userManager.getCurrentBoardOfUser(principal.getName());
        log.info("{} isClear: ? {}", message, userMap.isClear());
        if (userMap.isClear())
        {
//            final int userLevel = getUserLevel(principal.getName());
//            setUserLevel(principal.getName(), userLevel+1);
//            final Board nextLevel = service.getNextLevel(userLevel + 1);
//            if (nextLevel == null)
//            {
//                log.info("There is no more level");
//                replaceUserMap(principal.getName(), init(new String[][]{{"1"},
//                    {"0"}}, new NineByNineRule()));
//                return new MapInfo(new int[][]{{1},{0}}, false);
//            }
//            replaceUserMap(principal.getName(), nextLevel);
        }
        else
        {
            return new MapInfo(userMap.toArray(), userMap.isClear(), "");
        }
//        final Board newLevelBoard = getUserMap(principal.getName());
        final Board newLevelBoard = userManager.getNextBoardToUser(principal.getName());
        Initializer.debugMap(newLevelBoard);
        broadcastMapInfo();
        return new MapInfo(newLevelBoard.toArray(), newLevelBoard.isClear(), "5f6z5i4e4h43468a-94383d-gv422c");
    }

    private void broadcastMapInfo()
    {
        for (OAuth2AuthenticationToken name : sessions)
        {
            final Broadcast broadcast = new Broadcast();
            broadcast.setId(name.getName());
            broadcast.setMapInfo(userManager.getCurrentBoardOfUser(name.getName()).toArray());
            final Map<String, String> profile = getUserNicknameMap(name);
            broadcast.setName(profile.get("nickname"));
            messagingTemplate.convertAndSend("/queue/notify", broadcast);
        }
    }

    private Map<String, String> getUserNicknameMap(OAuth2AuthenticationToken name)
    {
        final Map<String, Object> kakaoAccount = ((UserPrincipal) (name.getPrincipal())).getAttribute("kakao_account");
        log.info("User sinfisdfnsidf ; {}", kakaoAccount);
        final Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        log.info("SDFSDF ; {}", profile.get("nickname"));
        return profile;
    }

    @Getter
    @Setter
    private class KakaoAccount
    {
        private boolean profileNeedsAgreement;
        @Getter
        @Setter
        private class Profile {
            private String nickname;
        }
        private Profile profile;
    }
}
