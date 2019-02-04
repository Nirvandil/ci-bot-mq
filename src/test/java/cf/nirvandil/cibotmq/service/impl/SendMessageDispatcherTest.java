package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.model.LongIdRepository;
import cf.nirvandil.cibotmq.model.Message;
import cf.nirvandil.cibotmq.service.ICiBot;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SendMessageDispatcherTest {

    @Test
    public void dispatch() {
        ICiBot bot = Mockito.mock(ICiBot.class);
        SendMessageDispatcher dispatcher = new SendMessageDispatcher(bot, Mockito.mock(LongIdRepository.class));
        dispatcher.dispatch(new Message("123", "127.0.0.1"));
        verify(bot, only()).sendToDevChat(eq("123"));
    }
}