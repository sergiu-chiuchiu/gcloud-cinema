package ro.gcloud.mycinema.exceptions;

public class BadRequestException extends MyCinemaGenericException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
