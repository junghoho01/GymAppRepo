package com.example.gymbetaapp
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {
    companion object {
        fun sendEmail(
            senderEmail: String,
            receiverEmail: String,
            password: String,
            verificationCode: String
        ) {
            val stringHost = "smtp.gmail.com"

            val properties = System.getProperties()

            properties.put("mail.smtp.host", stringHost)
            properties.put("mail.smtp.port", "465")
            properties.put("mail.smtp.ssl.enable", "true")
            properties.put("mail.smtp.auth", "true")

            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(senderEmail, password)
                }
            })

            val mimeMessage = MimeMessage(session)
            mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress(receiverEmail))

            mimeMessage.subject = "Configuration Check"

            val emailContent =
                "<html>" +
                        "<body>" +
                        "<div style='font-family: Arial, sans-serif;'>" +
                        "<h2>Password Reset Request</h2>" +
                        "<p>We received a request to reset your password. To proceed, please click the following link:</p>" +
                        "<p><a href='https://example.com/reset-password?code=$verificationCode'>Reset Password</a></p>" +
                        "<p>If you did not make this request, you can ignore this email, and your password will remain unchanged.</p>" +
                        "<p>Thank you!</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"

            mimeMessage.setText(emailContent, "UTF-8", "html")

            Thread {
                try {
                    Transport.send(mimeMessage)
                } catch (e: MessagingException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }
}
