package ro.gcloud.mycinema.exceptions;

public class MyCinemaGenericException extends Exception {

    public MyCinemaGenericException(String message){ super(message); }

    public MyCinemaGenericException(String message, Throwable cause){
        super(message, cause);
    }
}
