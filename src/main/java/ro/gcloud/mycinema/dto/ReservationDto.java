package ro.gcloud.mycinema.dto;

public class ReservationDto {
    private Long id;

    private Integer numberOfTickets;

    private Long customerId;

    private Long movieInstanceId;



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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMovieInstanceId() {
        return movieInstanceId;
    }

    public void setMovieInstanceId(Long movieInstanceId) {
        this.movieInstanceId = movieInstanceId;
    }
}
