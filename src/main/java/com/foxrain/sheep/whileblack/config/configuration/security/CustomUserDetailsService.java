package com.foxrain.sheep.whileblack.config.configuration.security;

import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.exception.ResourceNotFoundException;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.entity.User;
import com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.repository.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/01 1:13 오전 01 User we at 01 13 To change this
 * template use File | Settings | File Templates.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserMapper userRepository;

  public CustomUserDetailsService(UserMapper userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() ->
                         new UsernameNotFoundException("이 이메일에 해당하는 사용자가 없습니다. : " + email)
        );

    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository.getUserbyId(id).orElseThrow(
        () -> new ResourceNotFoundException("User", "id", id)
    );

    return UserPrincipal.create(user);
  }
}