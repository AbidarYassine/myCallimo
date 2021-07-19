package com.rest.ai.myCallimo.services.impl;


import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.RoleEntity;
import com.rest.ai.myCallimo.entities.UserEntity;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto userDto = userService.findByEmail(email);
        if (userDto == null) throw new UserNotFoundException("Email Or password incorrect");
        return new User(userDto.getEmail(), userDto.getEncryptedPassword(), new ArrayList<>());

    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        System.out.println("inside details impl " + user.getRole().toUpperCase());
        return grantedAuthorities;
    }

    //
    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {

        List<String> privileges = new ArrayList<>();

        for (RoleEntity role : roles) {
            privileges.add(role.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
