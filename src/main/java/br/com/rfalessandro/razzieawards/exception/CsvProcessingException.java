package br.com.rfalessandro.razzieawards.exception;

public class CsvProcessingException extends RuntimeException {

    public CsvProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
