
package utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class SendMailSSLWithAttachment extends BaseClass {

    public static void sendEmail() throws MessagingException {

        // Create object of Property file
        Properties props = new Properties();

        // this will set host of server- you can change based on your requirement
        props.put("mail.smtp.host", "smtp.gmail.com");

        // set the port of socket factory
        props.put("mail.smtp.socketFactory.port", "465");

        // set socket factory
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // set the authentication to true
        props.put("mail.smtp.auth", "true");

        // set the port of SMTP server
        props.put("mail.smtp.port", "465");

        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(props,

                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                     /*return new PasswordAuthentication(CredentialConstant.get_test_report_username(),
                                CredentialConstant.get_test_report_password());
                     */
                     return new PasswordAuthentication(CredentialConstant.get_test_report_username(),
                                CredentialConstant.get_test_report_password());

                    }
                });

        try {

            // Create object of MimeMessage class
            Message message = new MimeMessage(session);

            // Set the from address
            //message.setFrom(new InternetAddress(CredentialConstant.get_test_report_username()));
            message.setFrom(new InternetAddress(CredentialConstant.get_test_report_username()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("raghunandan.singh@psc-it.solutions"));
           //  message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("shivali.bakshi@seera.sa,shivali.bakshi@tothenew.com"));

            // Add the subject link
            message.setSubject(reportName);

            // Create object to add multimedia type content
            BodyPart messageBodyPart1 = new MimeBodyPart();
            BodyPart messageBodyPart3 = new MimeBodyPart();

            int pass = 0;
            int warning = 0;
            int skip = 0;
            int fail = 0;
            int error = 0;

            for (String value : testResultCount) {

                if (value.equalsIgnoreCase("pass"))
                    pass++;
                if (value.equalsIgnoreCase("warning"))
                    warning++;
                if (value.equalsIgnoreCase("skip"))
                    skip++;
                if (value.equalsIgnoreCase("fail"))
                    fail++;
                if (value.equalsIgnoreCase("error"))
                    error++;
            }

            messageBodyPart1.setContent(
                    "<h3>TEST EXECUTION SUMMARY</h3>\n" +
                            "<p style=\"color:black;\"><b>TOTAL TESTCASES : " + testResultCount.size() + "</b></p>\n" +
                            "<p style=\"color:green;\">PASS : " + pass + "</p>" +
                            "<p style=\"color:red;\">FAIL : " + fail + "</p>" +
                            "<p style=\"color:orange;\">WARNING : " + warning + "</p>" +
                            "<p style=\"color:grey;\">SKIP : " + skip + "</p>" +
                            "<p style=\"color:Tomato;\">ERROR : " + error + "</p>\n\n" +
                            "<p style=\"color:black;\">=>>  Please refer attached report for execution details</p>",

                    "text/html");
            messageBodyPart3.setContent("<h3>Execution Results: </h3>"+
                            "<p> " + reportMessage + "</p>\n" +
                            "<p style=\"color:black;\">=>>  Please refer attached report for execution details</p>",
                    "text/html");


            // Create another object to add another content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            File file = new File(reportPath);

            // Mention the file which you want to send
            String filename = file.getPath();


            // Create data source and pass the filename
            DataSource source = new FileDataSource(filename);

            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));

            // set the file
            messageBodyPart2.setFileName(reportName + ".html");

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();

            // add body part 1
            multipart.addBodyPart(messageBodyPart2);

            // add body part 2
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart3);
            // set the content
            message.setContent(multipart);

            // finally send the email
            Transport.send(message);

            System.out.println("=====Email Sent=====");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {

            throw new RuntimeException(e);

        }

    }

}