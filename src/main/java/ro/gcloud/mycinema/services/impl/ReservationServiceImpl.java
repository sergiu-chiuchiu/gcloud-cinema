package ro.gcloud.mycinema.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.entity.Reservation;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.ReservationRepository;
import ro.gcloud.mycinema.services.EmailService;
import ro.gcloud.mycinema.services.PersonService;
import ro.gcloud.mycinema.services.ReservationService;

import java.util.Collection;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    @Autowired
    private EmailService emailService;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Collection<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) throws NotFoundException {
        logger.info("Retrieving the Reservation with id=" + id);
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Reservation with id=%s was not found.", id)));
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        String receiver = null;
        try {
            receiver = personService.getPersonById(reservation.getPerson().getId()).getEmail();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        emailService.sendTestEmail(receiver);
        return savedReservation;
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        reservation.removeReservation();
        if (reservation.getMovieInstance() != null || reservation.getPerson() != null) {
            logger.error("Could not remove Reservation Child from one of its parents. The instance will not be deleted");
        }
        reservationRepository.delete(reservation);
    }
}
