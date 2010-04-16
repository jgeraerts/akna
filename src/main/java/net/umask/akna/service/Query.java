package net.umask.akna.service;

import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 29-dec-2009
 * Time: 14:25:24
 */
public interface Query<R> {

    R execute(Session session);

}
