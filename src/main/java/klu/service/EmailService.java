package klu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Secure Login OTP - WorkStudy Portal");
        message.setText("Hello,\n\nYour One-Time Password (OTP) for logging into the WorkStudy Portal is: " + otp + 
                        "\n\nThis OTP is valid for 5 minutes. Do not share this with anyone.\n\n- Admin Team");
        message.setFrom(senderEmail);

        mailSender.send(message);
    }
}
