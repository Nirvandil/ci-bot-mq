package cf.nirvandil.cibotmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Handlable {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private boolean handled = false;

    @NotNull
    private String ip;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    public Message(String message, String ip) {
        this.message = message;
        this.ip = ip;
    }

    @Override
    public boolean isHandled() {
        return this.handled;
    }
}
