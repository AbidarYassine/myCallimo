package com.rest.ai.myCallimo.dao;


import com.rest.ai.myCallimo.entities.SupervisorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorDao extends JpaRepository<SupervisorEntity, Integer> {
    public SupervisorEntity findByEmail(String email);

    //    @Query(value = "DELETE FROM supervisors s WHERE s.id=:id", nativeQuery = true)
//    public int deleteSupervisor(@Param("id") Integer id);
//
    public void deleteAllByEmail(String email);




}
