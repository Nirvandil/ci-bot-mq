package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.model.Description;
import cf.nirvandil.cibotmq.model.LongIdRepository;
import cf.nirvandil.cibotmq.service.AbstractMessageDispatcher;
import cf.nirvandil.cibotmq.service.IApplicationChecker;
import cf.nirvandil.cibotmq.service.IBambooClient;
import cf.nirvandil.cibotmq.service.ICiBot;
import cf.nirvandil.cibotmq.web.dto.BuildResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

@Slf4j
@Component
public class ExplainBuildDispatcher extends AbstractMessageDispatcher<Description> {

    private final IApplicationChecker checker;
    private final IBambooClient bambooClient;

    @Autowired
    public ExplainBuildDispatcher(ICiBot bot, LongIdRepository<Description> repository,
                                  IApplicationChecker checker, IBambooClient bambooClient) {
        super(bot, repository);
        this.checker = checker;
        this.bambooClient = bambooClient;
    }

    @Override
    @EventListener(classes = Description.class)
    public void dispatch(Description message) {
        log.info("Received explanation message {}", message);
        @NotNull String name = message.getBuildName();
        @NotNull Long number = message.getBuildNumber();
        CompletableFuture.supplyAsync(() -> bambooClient.getBuildResult(name, number))
                .thenApply(result -> result.map(BuildResultDTO::toString).orElse(failCheckStatusMessage(name, number)))
                .thenApply(bot::sendToDevChat)
                .thenAccept(handleAndSave(message));

        if (message.isShouldCheck()) {
            CompletableFuture.supplyAsync(checker::isUp).thenAccept(this::sendAppStatusMessage);
        }
    }

    private String failCheckStatusMessage(String name, Long number) {
        return format("Не удалось проверить статус сборки %s - %s. Возможно, она не существует", name, number);
    }

    private void sendAppStatusMessage(Boolean isUp) {
        if (isUp) {
            bot.sendToDevChat("Приложение находится в стабильном, рабочем состоянии ✅");
        } else {
            bot.sendToDevChat("Похоже, запуск приложения закончился неудачно ⛔");
        }
    }

}
