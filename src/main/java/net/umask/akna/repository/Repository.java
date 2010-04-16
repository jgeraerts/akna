package net.umask.akna.repository;

import java.io.Serializable;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 10:56:23
 */
public interface Repository {

    <T, PK extends Serializable> T get(Class<T> clazz, PK id);

    void save(Object o);

    void remove(Object o);

}
