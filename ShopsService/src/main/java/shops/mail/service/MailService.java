package shops.mail.service;

import org.thymeleaf.context.Context;
import shops.mail.model.Email;

public interface MailService {

    void sendHtml(String templateName, Email email, Context context);
    void send(Email email);
}
