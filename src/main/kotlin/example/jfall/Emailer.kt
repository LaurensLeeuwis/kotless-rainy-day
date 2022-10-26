package example.jfall

import example.jfall.EmailProperties.HOST
import example.jfall.EmailProperties.PROPS
import example.jfall.EmailProperties.SMTP_PASSWORD
import example.jfall.EmailProperties.SMTP_USERNAME
import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage

object Emailer {
    fun sendEmail(subject: String, body: String, fromAddress: String,
                  fromName: String, toAddress: String) : Boolean {
        val session: Session = Session.getDefaultInstance(PROPS)
        val msg = MimeMessage(session)
        msg.setFrom(InternetAddress(fromAddress, fromName))
        msg.setRecipient(Message.RecipientType.TO, InternetAddress(toAddress))
        msg.setSubject(subject)
        msg.setContent(body, "text/html")
        val transport: Transport = session.getTransport()
        try {
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD)
            transport.sendMessage(msg, msg.getAllRecipients())
            return true
        } catch (ex: Exception) {
            return false
        } finally {
            transport.close()
        }
    }
}
