package rs.dev.plasticstore.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import rs.dev.plasticstore.model.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Async
    public void sendOrderConfirmationEmail(Mail mail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("order", mail.getOrder());
        context.setVariable("plastic_store_link", mail.getHome_link());
        String html = templateEngine.process("mail/order_confirmation_mail", context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.addInline("logoImage", logoImage);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

    @Async
    public void sendOrderEmail(Mail mail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("order", mail.getOrder());
        context.setVariable("plastic_store_link", mail.getHome_link());
        String html = templateEngine.process("mail/order_notification_mail", context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.addInline("logoImage", logoImage);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

    @Async
    public void sendResetPasswordEmail(Mail mail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("reset_link", mail.getReset_link());
        context.setVariable("plastic_store_link", mail.getHome_link());
        String html = templateEngine.process("mail/reset_password_mail", context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.addInline("logoImage", logoImage);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

    @Async
    public void sendSimpleEmailMessage(SimpleMailMessage message) {
        mailSender.send(message);
    }

    @Autowired
    private JavaMailSender mailSender;
    @Value("classpath:static/images/logo.png")
    private Resource logoImage;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

}
