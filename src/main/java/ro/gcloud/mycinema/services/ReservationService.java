package ro.gcloud.mycinema.services;

import ro.gcloud.mycinema.entity.Reservation;
import ro.gcloud.mycinema.exceptions.NotFoundException;

import java.util.Collection;

public interface ReservationService {


    Collection<Reservation> getAllReservations();

    Reservation getReservationById(Long id) throws NotFoundException;

    Reservation saveReservation(Reservation reservation);

    void deleteReservation(Reservation reservation);
}
