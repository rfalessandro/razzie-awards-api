package br.com.rfalessandro.razzieawards.exception;

public class AwardCategoryNotFoundException extends RuntimeException {
    public AwardCategoryNotFoundException(String category) {
        super("Award category " + category + " not found");
    }
}
