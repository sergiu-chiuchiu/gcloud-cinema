package ro.gcloud.mycinema.scheduledtasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.gcloud.mycinema.dto.MailData;
import ro.gcloud.mycinema.dto.MailResponse;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.entity.Reservation;
import ro.gcloud.mycinema.repositories.MovieInstanceRepository;
import ro.gcloud.mycinema.services.impl.EmailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ScheduledEmail {
    private Logger logger = LogManager.getLogger(this.getClass());
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private EmailService emailService;
    private MovieInstanceRepository movieInstanceRepository;

    @Autowired
    public ScheduledEmail(EmailService emailService, MovieInstanceRepository movieInstanceRepository) {
        this.emailService = emailService;
        this.movieInstanceRepository = movieInstanceRepository;
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void testing() {

        logger.info("7 o'clock scheduled email :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

        Map<String, MailData> listMailData = new HashMap<>();
        Map<String, Object> mapOfModel = new HashMap<>();

        List<MovieInstance> allMovieInstances = movieInstanceRepository.findAllMovieInstances(new Date());
        for (MovieInstance mi : allMovieInstances) {

            for (Reservation r : mi.getReservations()) {

                String email = r.getPerson().getEmail();
                // we make sure that even if the customer has multiple reservations that day
                // he will only receive one email (I know that is not the nicest solution :) )
                if (listMailData.containsKey(email)) {
                    continue;
                } else {
                    MailData md = new MailData();
                    md.setSubject("Today Movie Reservation Reminder");
                    md.setTo(email);
                    listMailData.put(email, md);

                    Map<String, Object> mod = new HashMap<>();

                    mod.put("Name", r.getPerson().getFullName());
                    mod.put("phone", r.getPerson().getPhone());
                    mapOfModel.put(email, mod);
                }
            }
        }

        for (String emailKey : listMailData.keySet()) {
            logger.info("Sending movie reminder email to: " + emailKey);
            MailResponse mr = emailService.sendEmail(listMailData.get(emailKey), (Map<String, Object>) mapOfModel.get(emailKey));

            if (mr.isStatus()) {
                logger.info("Email sent successfully to: " + emailKey);
            } else {
                logger.warn("Could not send email to: " + emailKey);
            }
        }
        logger.info("Scheduled email sent successfully :: Finish Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

}
