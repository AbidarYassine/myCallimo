package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    public Role findByName(String name);

}
