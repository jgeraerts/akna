package net.umask.akna.service;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.umask.akna.commandhandlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 15:50:06
 */

@Service
@Transactional
public class CommandDispatcherImpl implements CommandDispatcher {

    private final Multimap<Class<?>, CommandHandler<?>> handlers;

    @Autowired
    public CommandDispatcherImpl(List<CommandHandler<?>> handlers) {
        ImmutableMultimap.Builder<Class<?>, CommandHandler<?>> multimapbuilder = ImmutableMultimap.builder();
        for (CommandHandler<?> handler : handlers) {
            multimapbuilder.put(handler.getType(), handler);
        }
        this.handlers = multimapbuilder.build();

    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void dispatchCommand(Object command) {

        Collection<CommandHandler<?>> handlers = this.handlers.get(command.getClass());
        if (handlers.isEmpty()) {
            throw new IllegalStateException("No handler for command of class " + command.getClass().getName());
        }
        for (CommandHandler handler : handlers) {
            handler.handleMessage(command);
        }


    }
}
