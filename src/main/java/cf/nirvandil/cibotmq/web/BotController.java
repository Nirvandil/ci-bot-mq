package cf.nirvandil.cibotmq.web;

import cf.nirvandil.cibotmq.model.Description;
import cf.nirvandil.cibotmq.model.Message;
import cf.nirvandil.cibotmq.web.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BotController {
    private final ApplicationEventPublisher publisher;

    @PostMapping("/explain/{buildNum}/{buildName}")
    @ResponseStatus(CREATED)
    public void explainBuild(@PathVariable Long buildNum, @PathVariable String buildName,
                             @RequestParam(value = "checkApp", required = false, defaultValue = "false") Boolean checkApp,
                             HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Received request from {} for explain build {} of {} build plan", ip, buildNum, buildName);
        publisher.publishEvent(new Description(buildName, buildNum, ip, checkApp));
    }

    @PostMapping("/message")
    @ResponseStatus(CREATED)
    public void sendMessage(@Valid @RequestBody MessageDTO messageDTO, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.debug("Received request for send message from {}", ip);
        publisher.publishEvent(new Message(messageDTO.getMessage(), ip));
    }
}
