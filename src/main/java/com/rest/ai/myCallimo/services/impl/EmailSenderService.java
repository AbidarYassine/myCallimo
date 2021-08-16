package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.shared.EMail;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class EmailSenderService {

//    https://stackoverflow.com/questions/59049114/how-to-send-mail-using-spring-batch


    private JavaMailSender mailSender;
    @Qualifier("getFreeMarkerConfiguration")
    private Configuration fmConfiguration;

    public EmailSenderService(JavaMailSender mailSender, Configuration fmConfiguration) {
        this.mailSender = mailSender;
        this.fmConfiguration = fmConfiguration;
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
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(eMail.getSubject());
            mimeMessageHelper.setFrom(eMail.getFrom());
            mimeMessageHelper.setTo(eMail.getTo());
            eMail.setContent(geContentFromTemplate(eMail.getData()));
            mimeMessageHelper.setText(eMail.getContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
