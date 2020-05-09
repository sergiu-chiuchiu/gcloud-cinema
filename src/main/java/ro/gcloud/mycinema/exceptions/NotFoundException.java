package ro.gcloud.mycinema.exceptions;

public class NotFoundException extends MyCinemaGenericException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
