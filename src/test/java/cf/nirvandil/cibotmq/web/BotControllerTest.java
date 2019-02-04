package cf.nirvandil.cibotmq.web;

import cf.nirvandil.cibotmq.web.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(SpringRunner.class)
@WebMvcTest(BotController.class)
@ActiveProfiles("test")
public class BotControllerTest {
    @MockBean
    private ApplicationEventPublisher publisher;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void explainBuild() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/api/explain/1/BACKEND")
                        .with(httpBasic("admin", "Aaaaaaa1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void sendMessage() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/api/message")
                        .with(httpBasic("admin", "Aaaaaaa1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(new MessageDTO("test")))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}