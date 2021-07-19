package com.rest.ai.myCallimo.services.facade;


import java.util.List;

public interface BaseInterface<T> {

    T save(T t);

    List<T> findAll();

    T findById(Integer id);

}
