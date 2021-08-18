package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.shared.EMail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSenderService {

//    https://stackoverflow.com/questions/59049114/how-to-send-mail-using-spring-batch


    private final JavaMailSender mailSender;
    @Qualifier("getFreeMarkerConfiguration")
    private final TemplateEngine templateEngine;

    public EmailSenderService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");

    }


    public void sendEmail(EMail eMail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("email", eMail);
        String process = templateEngine.process("email", context);
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(eMail.getSubject());
            mimeMessageHelper.setFrom(eMail.getFrom());
            mimeMessageHelper.setTo(eMail.getTo());
            mimeMessageHelper.setText(process, true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

//    public String geContentFromTemplate(Map<String, Object> model) {
//        StringBuffer content = new StringBuffer();
//
//        try {
//            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email"), model));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }
}
