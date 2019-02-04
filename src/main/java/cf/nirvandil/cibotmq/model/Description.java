package cf.nirvandil.cibotmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.ApplicationEvent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Description implements Handlable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String buildName;

    @NotNull
    private Long buildNumber;

    @NotNull
    private String ip;

    @NotNull
    private boolean handled = false;

    @NotNull
    private boolean shouldCheck;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    public Description(String buildName, Long buildNum, String ip, boolean shouldCheck) {
        this.buildName = buildName;
        this.buildNumber = buildNum;
        this.ip = ip;
        this.shouldCheck = shouldCheck;
    }

    @Override
    public boolean isHandled() {
        return this.handled;
    }
}
