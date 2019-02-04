package cf.nirvandil.cibotmq.web.dto;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


public class BuildResultDTOTest {

    @Test
    public void toStringTest() {
        BuildResultDTO dto = new BuildResultDTO(true, 42L, new ChangesDTO(Collections.emptyList()));
        assertThat(dto.toString())
                .contains("Сборка номер 42 завершилась успешно");
        dto.setChanges(new ChangesDTO(Collections.singletonList(new ChangeDTO("testComment", null, null))));
        assertThat(dto.toString())
                .contains("testComment");
        dto.setSuccessful(false);
        assertThat(dto.toString())
                .contains("К сожалению");
    }
}