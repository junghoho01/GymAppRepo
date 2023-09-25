package com.example.gymbetaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // Retrieve the verification code from the intent
        String verificationCode = getIntent().getStringExtra("verification_code");

        // Call the send email function with the verification code
        buttonSendEmail(verificationCode);
    }

    public void buttonSendEmail(String verificationCode){

        javax.mail.Session mailSession;
        android.se.omapi.Session androidSession;

        try {
            String stringSenderEmail = "phuajunhau.pola2001@gmail.com";
            String stringReceiverEmail = "phuawork@gmail.com";
            String stringPasswordSenderEmail = "fziiygnfzjlgsrfr";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Configuration Check");
// Generate a unique verification code (you can use a library for this)
//            String verificationCode = "123456"; // Replace with an actual code

// The email content in HTML format
            String emailContent = "<html>" +
                    "<body>" +
                    "<div style='font-family: Arial, sans-serif;'>" +
                    "<h2>Password Reset Request</h2>" +
                    "<p>We received a request to reset your password. To proceed, please click the following link:</p>" +
                    "<p><a href='https://example.com/reset-password?code=" + verificationCode + "'>Reset Password</a></p>" +
                    "<p>If you did not make this request, you can ignore this email, and your password will remain unchanged.</p>" +
                    "<p>Thank you!</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            mimeMessage.setText(emailContent, "UTF-8", "html");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}