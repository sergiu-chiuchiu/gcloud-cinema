package ro.gcloud.mycinema.dto;

import java.util.Date;

public class MovieInstanceDto {

    private Long id;

    private Date startDate;

    private Date endDate;

    private Integer availableSeats;

    private Long cinemaId;

    private Long movieId;

    private Long movieRoomId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieRoomId() {
        return movieRoomId;
    }

    public void setMovieRoomId(Long movieRoomId) {
        this.movieRoomId = movieRoomId;
    }
}
