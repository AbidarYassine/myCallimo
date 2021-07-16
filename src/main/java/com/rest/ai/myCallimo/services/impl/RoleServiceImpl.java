package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.RoleDao;
import com.rest.ai.myCallimo.dto.RoleDto;
import com.rest.ai.myCallimo.entities.RoleEntity;
import com.rest.ai.myCallimo.services.facade.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public RoleDto findByName(String name) {
        ModelMapper modelMapper = new ModelMapper();
        RoleEntity role = roleDao.findByName(name);
        if (role == null)
            return null;
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto save(RoleDto role) {
        RoleEntity roleF = roleDao.findByName(role.getName());
        if (roleF != null) return null;
        ModelMapper modelMapper = new ModelMapper();
        RoleEntity role_to_save = modelMapper.map(role, RoleEntity.class);
        roleDao.save(role_to_save);
        return role;
    }
}
