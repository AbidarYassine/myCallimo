package com.rest.ai.myCallimo.services.facade;


import java.util.List;

/***
 * Class description:
 * Base Interface contain base Method of a generic type
 * @param <T> Dto Generic
 */

public interface BaseInterface<T> {

    /***
     * Save
     * @param t Generic Dto
     * @return Dto
     */
    T save(T t);

    /***
     * get All
     * @return
     */
    List<T> findAll();

    /***
     *find by ID
     * @param id search by id;
     * @return
     */
    T findById(Integer id);

}
