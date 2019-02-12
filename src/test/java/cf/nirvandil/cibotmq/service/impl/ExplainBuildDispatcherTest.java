package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.model.Description;
import cf.nirvandil.cibotmq.model.LongIdRepository;
import cf.nirvandil.cibotmq.service.IApplicationChecker;
import cf.nirvandil.cibotmq.service.IBambooClient;
import cf.nirvandil.cibotmq.service.ICiBot;
import cf.nirvandil.cibotmq.web.dto.BuildResultDTO;
import cf.nirvandil.cibotmq.web.dto.ChangesDTO;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ExplainBuildDispatcherTest {
    @Test
    public void dispatch() throws InterruptedException {
        ICiBot ciBot = mock(ICiBot.class);
        LongIdRepository repository = mock(LongIdRepository.class);
        IApplicationChecker checker = mock(IApplicationChecker.class);
        IBambooClient client = mock(IBambooClient.class);
        when(client.getBuildResult(eq("EGIP-BACK"), eq(1142L)))
                .thenReturn(Optional.of(new BuildResultDTO(true, 1142L, new ChangesDTO(Collections.emptyList()), null)));
        when(ciBot.sendToDevChat(anyString()))
                .thenReturn(true);
        when(checker.isUp())
                .thenReturn(true);
        ExplainBuildDispatcher dispatcher = new ExplainBuildDispatcher(ciBot, repository, checker, client);
        dispatcher.dispatch(new Description("EGIP-BACK", 1142L, "127.0.0.1", true));
        verify(checker, only()).isUp();
        verify(ciBot, atMost(2)).sendToDevChat(anyString());
        verify(ciBot, atMost(1)).sendToDevChat(eq("Приложение находится в стабильном, рабочем состоянии ✅"));
    }
}