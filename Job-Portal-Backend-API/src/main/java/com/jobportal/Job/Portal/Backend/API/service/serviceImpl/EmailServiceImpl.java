package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;

import com.jobportal.Job.Portal.Backend.API.constant.FreeMarkerTemplateConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    public void sendOtpMail(String email, String name, String otp, String passwordResetLink) {
        try {

            Template template = freemarkerConfig.getTemplate("otpTemplate");

            Map<String, Object> model = new HashMap<>();
            model.put(FreeMarkerTemplateConstant.NAME, name);
            model.put(FreeMarkerTemplateConstant.OTP, otp);
            model.put(FreeMarkerTemplateConstant.EXPIRATION_TIME, 5);
            model.put(FreeMarkerTemplateConstant.CURRENT_YEAR, Year.now().getValue());
            model.put(FreeMarkerTemplateConstant.PASSWORD_RESET_LINK, passwordResetLink);

            String html = processTemplate(template, model);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Password Reset OTP - Job Portal");
            helper.setText(html, true);

            mailSender.send(message);

            log.info("OTP email sent successfully to: {}", email);

        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", email, e);
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    private String processTemplate(Template template, Map<String, Object> model) {
        try (java.io.StringWriter writer = new java.io.StringWriter()) {
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Template processing failed", e);
        }
    }
}