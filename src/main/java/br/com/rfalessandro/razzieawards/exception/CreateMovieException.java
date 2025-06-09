package br.com.rfalessandro.razzieawards.exception;

public class CreateMovieException extends RuntimeException {
    public CreateMovieException(String message, Throwable cause) {
        super(message, cause);
    }
}
