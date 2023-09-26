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
            receiverEmail: String?,
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

            mimeMessage.subject = "Account Verification"

            val emailContent = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Filtration - Account Verification</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">

                <!-- Header -->
                <table style="width: 100%; background-color: #2ecc71; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <h1 style="color: #fff;">Filtration</h1>
                        </td>
                    </tr>
                </table>

                <!-- Content -->
                <table style="width: 80%; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px 0px #888888;">
                    <tr>
                        <td>
                            <h2>Account Verification</h2>
                            <p>Thank you for registering with Filtration Fitness!</p>
                            <p>To activate your account, please enter the verification code as below:</p>
                            <p><a style="background-color: #2ecc71; color: #fff; text-decoration: none; padding: 10px 20px; border-radius: 5px;" disabled>$verificationCode</a></p>
                            <p>If you did not create this account, you can ignore this email.</p>
                            <p>Thank you for choosing Filtration Fitness!</p>
                        </td>
                    </tr>
                </table>

                <!-- Footer -->
                <table style="width: 100%; background-color: #2ecc71; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <p style="color: #fff;">&copy; 2023 Filtration Fitness</p>
                        </td>
                    </tr>
                </table>

            </body>
            </html>
        """.trimIndent()

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
