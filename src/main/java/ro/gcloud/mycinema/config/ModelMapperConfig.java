package ro.gcloud.mycinema.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.gcloud.mycinema.dto.MovieInstanceDto;
import ro.gcloud.mycinema.dto.MovieRoomDto;
import ro.gcloud.mycinema.dto.ReservationDto;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.entity.Reservation;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.typeMap(MovieRoomDto.class, MovieRoom.class).addMappings(n -> {
            n.<Long>map(MovieRoomDto::getCinema_id, (MovieRoom, v) -> MovieRoom.getCinema().setId(v));
        });

        modelMapper.typeMap(ReservationDto.class, Reservation.class).addMappings(n -> {
            n.<Long>map(ReservationDto::getCustomerId, (Reservation, v) -> Reservation.getPerson().setId(v));
        });

        modelMapper.typeMap(ReservationDto.class, Reservation.class).addMappings(n -> {
            n.<Long>map(ReservationDto::getMovieInstanceId, (Reservation, v) -> Reservation.getMovieInstance().setId(v));
        });



        modelMapper.typeMap(MovieInstanceDto.class, MovieInstance.class).addMappings(n -> {
            n.<Long>map(MovieInstanceDto::getCinemaId, (MovieInstance, v) -> MovieInstance.getCinema().setId(v));
        });

        modelMapper.typeMap(MovieInstanceDto.class, MovieInstance.class).addMappings(n -> {
            n.<Long>map(MovieInstanceDto::getMovieId, (MovieInstance, v) -> MovieInstance.getMovie().setId(v));
        });

        modelMapper.typeMap(MovieInstanceDto.class, MovieInstance.class).addMappings(n -> {
            n.<Long>map(MovieInstanceDto::getMovieRoomId, (MovieInstance, v) -> MovieInstance.getMovieRoom().setId(v));
        });


        return modelMapper;
    }
}
