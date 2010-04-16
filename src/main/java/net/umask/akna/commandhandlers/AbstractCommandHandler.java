package net.umask.akna.commandhandlers;

import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 15:14:32
 */
public abstract class AbstractCommandHandler<T> implements CommandHandler<T> {
    private Class<T> commandType;

    @Autowired
    private Repository repository;

    @SuppressWarnings({"unchecked"})
    public AbstractCommandHandler() {
        this.commandType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<T> getType() {
        return commandType;
    }

    public Repository getRepository() {
        return repository;
    }
}
