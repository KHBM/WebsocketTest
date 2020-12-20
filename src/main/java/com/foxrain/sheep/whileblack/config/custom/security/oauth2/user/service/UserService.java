package com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.service;

import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.entity.User;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.repository.UserMapper;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with intellij IDEA.
 * by 2020 12 2020/12/19 3:57 오후 19
 * User we at 15 57
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    @PostConstruct
    public void post()
    {
        log.info("WHIHWHWIHEWIHWIHWIHWIWHIWHWIHWIHWIHWIHWIWHIWHIWHIWH");
    }

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    /**
     * 사용자가 등록되었었는지를 확인합니다.
     * @param user
     * @return 기존에 존재할 경우 참값.
     */
    @Transactional
    public boolean isUserExist(User user) {
        final User userExist = userMapper.isUserExist(user);

        log.info("User found result is : {}", userExist);

        if (userExist == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 사용자를 등록합니다.
     * @param user
     * @return 성공 유무
     */
    @Transactional
    public boolean registerUser(User user) {
        final int insertCount = userMapper.insertUser(user);
        log.info("User inserted count affected : {}", insertCount);
        if (insertCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Optional<User> findById(Long id) {
        return userMapper.getUserbyId(id);
    }
}
