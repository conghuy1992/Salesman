package com.orion.salesman._class;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Dat on 10/10/2016.
 */
public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");// 587
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body,
                                      String senderEmail, ArrayList<String> recipients, ArrayList<File> files)
            throws Exception {


        String from = senderEmail;

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        for (String string : recipients) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    string));
        }

        message.setSubject(subject);

        // create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        // fill message
        messageBodyPart.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // part three for logs
        for (File file : files) {
            addAttachment(multipart, file);

        }

        // Put parts in message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients, File attachment) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);

            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(body);

            MimeBodyPart mbp2 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            message.setContent(mp);

            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            Transport.send(message);
        } catch (Exception e) {

        }
    }

    public synchronized void sendMail(String subject, String body,
                                      String sender, String recipients) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(
                    body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipients));
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("GMailSender exception", e.getMessage());
        }
    }

    public synchronized void sendMail(String subject, String body,
                                      String senderEmail, String recipients, String filePath,
                                      String logFilePath) throws Exception {
        boolean fileExists = new File(filePath).exists();
        if (fileExists) {

            String from = senderEmail;
            String to = recipients;
            String fileAttachment = filePath;

            // Define message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            message.setSubject(subject);

            // create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // fill message
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(fileAttachment);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("screenShoot.jpg");
            multipart.addBodyPart(messageBodyPart);

            // part three for logs
            messageBodyPart = new MimeBodyPart();
            DataSource sourceb = new FileDataSource(logFilePath);
            messageBodyPart.setDataHandler(new DataHandler(sourceb));
            messageBodyPart.setFileName("logs.txt");
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
        } else {
            sendMail(subject, body, senderEmail, recipients);
        }
    }

    public synchronized void sendMail(String subject, String body,
                                      String senderEmail, String recipients, String logFilePath)
            throws Exception {

        File file = new File(logFilePath);
        boolean fileExists = file.exists();
        if (fileExists) {

            String from = senderEmail;
            String to = recipients;

            // Define message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            message.setSubject(subject);

            // create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // fill message
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // part three for logs

            messageBodyPart = new MimeBodyPart();
            DataSource sourceb = new FileDataSource(logFilePath);
            messageBodyPart.setDataHandler(new DataHandler(sourceb));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
        } else {
            sendMail(subject, body, senderEmail, recipients);
        }
    }

    public synchronized void sendMail(String subject, String body,
                                      String senderEmail, String recipients, ArrayList<File> files)
            throws Exception {

        //File file = new File(logFilePath);
        //boolean fileExists = file.exists();
        //if (fileExists) {

        String from = senderEmail;
        String to = recipients;

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                to));
        message.setSubject(subject);

        // create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        // fill message
        messageBodyPart.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // part three for logs
        for (File file : files) {
            addAttachment(multipart, file);

        }
//            messageBodyPart = new MimeBodyPart();
//            DataSource sourceb = new FileDataSource(logFilePath);
//            messageBodyPart.setDataHandler(new DataHandler(sourceb));
//            messageBodyPart.setFileName(file.getName());
//            multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);
        //} else {
        //    sendMail(subject, body, senderEmail, recipients);
        //}
    }

    private static void addAttachment(Multipart multipart, File file) {
        DataSource source = new FileDataSource(file.getPath());
        BodyPart messageBodyPart = new MimeBodyPart();
        try {
            messageBodyPart.setDataHandler(new DataHandler(source));

            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}