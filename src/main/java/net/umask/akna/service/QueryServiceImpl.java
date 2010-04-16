package net.umask.akna.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * User: Me
 * Date: 29-dec-2009
 * Time: 17:50:02
 */
@Service
@Transactional(readOnly = true)
public class QueryServiceImpl implements QueryService {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public <R> R executeQuery(Query<R> query) {
        return query.execute(sessionFactory.getCurrentSession());
    }
}
