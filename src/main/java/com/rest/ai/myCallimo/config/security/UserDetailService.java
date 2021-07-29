package com.rest.ai.myCallimo.config.security;



import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailService(UserService userService) {
        this.userService = userService;
    }


    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userDao.findByEmail(email);
//        if (userEntity == null) throw new UsernameNotFoundException(email);
//        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
//    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto userDto = userService.findByEmail(email);
        if (userDto == null) throw new UserNotFoundException("Email Or password incorrect");
        return CustomUserDetails.build(userDto);

    }

}
