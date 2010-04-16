package net.umask.akna.commandhandlers;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 14:49:49
 */
public interface CommandHandler<T> {

    Class<T> getType();

    void handleMessage(T command);
}
