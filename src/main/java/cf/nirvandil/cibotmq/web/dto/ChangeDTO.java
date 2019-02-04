package cf.nirvandil.cibotmq.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ChangeDTO {
    private String comment;
    private String commitUrl;
    private String author;
}
