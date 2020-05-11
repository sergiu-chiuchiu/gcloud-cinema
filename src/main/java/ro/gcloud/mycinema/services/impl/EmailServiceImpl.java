package ro.gcloud.mycinema.services.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.repositories.ReservationRepository;
import ro.gcloud.mycinema.services.EmailService;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public String sendTestEmail(String receiver) {
        Email from = new Email("test@example.com");
        String subject = "Reservation confirmation";
        Email to;
        System.out.println("Sending email to: " + receiver);
        if (receiver != null) {
            to = new Email(receiver);
        } else {
            to = new Email("sergiu.chiuchiu@gmail.com");
        }
        Content content = new Content("text/plain", "Hello, thank you for your reservation!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Exception");;
        } finally {
            return "Email sent";
        }
    }
}
