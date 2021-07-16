package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.RoleDao;
import com.rest.ai.myCallimo.dao.UserDao;
import com.rest.ai.myCallimo.entities.Role;
import com.rest.ai.myCallimo.entities.UserEntity;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
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
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserDetailService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userDao.findByEmail(email);
//        if (userEntity == null) throw new UsernameNotFoundException(email);
//        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        UserEntity user = userDao.findByEmail(email);
//        if (user == null) {
//            throw new UserNotFoundException("Email Or password incorrect");
//        } else {
//            return new org.springframework.security.core.userdetails.User(
//                    user.getEmail(), user.getEncryptedPassword(), true, true, true,
//                    true, getAuthorities(user));
//        }
        UserEntity userEntity = userDao.findByEmail(email);
        if (userEntity == null) throw new UserNotFoundException("Email Or password incorrect");
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase()));
        System.out.println("inside details impl " + user.getRole().getName().toUpperCase());
        return grantedAuthorities;
    }

    //
    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();

        for (Role role : roles) {
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
