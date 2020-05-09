package ro.gcloud.mycinema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_tickets")
    private Integer numberOfTickets;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "person")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "movie_instance_id")
    @JsonBackReference(value = "movieInstance")
    private MovieInstance movieInstance;

    public void removeReservation() {
        this.getPerson().getReservations().remove(this);
        this.setPerson(null);

        this.getMovieInstance().getReservations().remove(this);
        this.setMovieInstance(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public MovieInstance getMovieInstance() {
        return movieInstance;
    }

    public void setMovieInstance(MovieInstance movieInstance) {
        this.movieInstance = movieInstance;
    }
}
