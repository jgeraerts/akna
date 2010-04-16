package net.umask.akna.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 10:58:16
 */

@org.springframework.stereotype.Repository
public class RepositoryHibernate implements Repository {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings({"unchecked"})
    @Override
    public <T, PK extends Serializable> T get(Class<T> clazz, PK id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    @Override
    public void save(Object o) {
        sessionFactory.getCurrentSession().save(o);
    }

    @Override
    public void remove(Object o) {
        sessionFactory.getCurrentSession().delete(o);
    }
}
