package shops.mail.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import shops.mail.model.Email;
import shops.mail.repository.EmailRepository;
import shops.mail.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;
    private final TemplateEngine engine;

    @Override
    public void sendHtml(String templateName, Email email, Context context) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String body = engine.process(templateName, context);
            helper.setFrom(email.getSender());
            email.setContent(body);
            helper.setTo(email.getReceiver());
            helper.setSubject(email.getSubject());
            helper.setSentDate(new Date());
            helper.setText(body, true);
            mailSender.send(mimeMessage);
            emailRepository.save(email);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Email email) {

    }
}
