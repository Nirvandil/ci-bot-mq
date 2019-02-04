package cf.nirvandil.cibotmq.service;

import cf.nirvandil.cibotmq.model.Handlable;
import cf.nirvandil.cibotmq.model.LongIdRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public abstract class AbstractMessageDispatcher<T extends Handlable> implements IMessageDispatcher<T> {
    protected final ICiBot bot;
    private final LongIdRepository<T> repository;

    public AbstractMessageDispatcher(ICiBot bot, LongIdRepository<T> repository) {
        this.bot = bot;
        this.repository = repository;
    }

    protected Consumer<? super Boolean> handleAndSave(T source) {
        return result -> {
            if (result) {
                source.setHandled(true);
                log.info("Successfully handled source {}, saving it to history", source);
            } else {
                source.setHandled(false);
                log.error("Source {} was not handled, saving it to feature recovery", source);
            }
            repository.save(source);
        };
    }
}
