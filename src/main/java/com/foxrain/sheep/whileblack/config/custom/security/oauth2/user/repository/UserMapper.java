package com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.repository;

import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.entity.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created with intellij IDEA.
 *
 * @author foxrain
 * @created 2020/12/19 3:54 오후
 */
@Repository
@Mapper
public interface UserMapper {

    User isUserExist(User user);

    int insertUser(User user);

    int updateUser(User user);

    Optional<User> getUserbyId(Long id);

    Optional<User> findByEmail(String email);

}
