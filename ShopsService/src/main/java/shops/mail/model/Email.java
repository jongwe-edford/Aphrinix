package shops.mail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Email {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String content;
    private String subject;
    private LocalDateTime sendTime;
    private String originIpAddress;
}
