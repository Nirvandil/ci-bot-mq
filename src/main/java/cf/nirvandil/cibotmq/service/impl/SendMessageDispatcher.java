package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.model.LongIdRepository;
import cf.nirvandil.cibotmq.model.Message;
import cf.nirvandil.cibotmq.service.AbstractMessageDispatcher;
import cf.nirvandil.cibotmq.service.ICiBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SendMessageDispatcher extends AbstractMessageDispatcher<Message> {

    @Autowired
    public SendMessageDispatcher(ICiBot bot, LongIdRepository<Message> repository) {
        super(bot, repository);
    }

    @Override
    @EventListener(Message.class)
    public void dispatch(Message message) {
        log.debug("Received message for send {}", message);
        CompletableFuture.supplyAsync(() -> bot.sendToDevChat(message.getMessage()))
                .thenAccept(handleAndSave(message));
    }

}
