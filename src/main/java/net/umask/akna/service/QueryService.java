package net.umask.akna.service;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 29-dec-2009
 * Time: 14:20:24
 */
public interface QueryService {

    <R> R executeQuery(Query<R> query);

}
