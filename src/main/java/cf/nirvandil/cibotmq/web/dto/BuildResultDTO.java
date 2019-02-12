package cf.nirvandil.cibotmq.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildResultDTO {
    private Boolean successful;
    private Long buildNumber;
    private ChangesDTO changes;
    private String buildResultKey;

    public static BuildResultDTO empty(Long id) {
        return new BuildResultDTO(true, id, new ChangesDTO(Collections.emptyList()), null);
    }

    @Override
    public String toString() {
        if (this.successful) {
            StringBuilder builder = new StringBuilder(142);
            builder.append("Сборка номер ").append(this.buildNumber).append(" завершилась успешно. ✅ \n");
            if (!changes.getChange().isEmpty()) {
                builder.append("===============================================\n").append("Список изменений:\n");
                changes.getChange().forEach(
                        change -> builder.append(change.getAuthor()).append(": \n").append(change.getComment()).append("\n")
                );
                builder.append("===============================================\n");
            }
            return builder.toString();
        } else {
            return "К сожалению, сборка номер " + buildNumber + " завершилась неудачно. ⛔️\n" +
                    "Подробности можно найти на странице " + System.getenv("CI_BASE_URL") + "/browse/" + buildResultKey + "/log.";
        }
    }
}