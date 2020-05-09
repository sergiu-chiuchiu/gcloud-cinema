package ro.gcloud.mycinema.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gcloud.mycinema.dto.ReservationDto;
import ro.gcloud.mycinema.entity.Reservation;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.ReservationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    private Logger logger = LogManager.getLogger(this.getClass());


    @GetMapping
    public Collection<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping(value = "/{id}")
    public Reservation getReservationById(@PathVariable("id") Long id) throws NotFoundException {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationDto reservationToSave) throws URISyntaxException {
        Reservation reservation = reservationService.saveReservation(modelMapper.map(reservationToSave, Reservation.class));
        return ResponseEntity.created(new URI("/api/reservations/" + reservation.getId())).body(reservation);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationToUpdate) throws BadRequestException, NotFoundException, URISyntaxException {
        if (!id.equals(reservationToUpdate.getId())) {
            logger.warn("The ids do not match: received id=" + id + " in path and id=" + reservationToUpdate.getId() + " in entity!");
            throw new BadRequestException("Different ids: " + id + " from PathVariable and " + reservationToUpdate.getId() + " from RequestBody");
        }
        Reservation reservationDb = reservationService.getReservationById(id);
        modelMapper.map(reservationToUpdate, reservationDb);
        reservationService.saveReservation(reservationDb);
        return ResponseEntity.ok(new URI("/api/reservations/" + reservationDb.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id) throws NotFoundException {
        logger.info("Deleting Reservation with id=" + id);
        Reservation reservation = reservationService.getReservationById(id);
        reservationService.deleteReservation(reservation);
    }


}
