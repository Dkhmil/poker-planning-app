package com.khmil.management.exception;

public class PokerPlanningSessionNotFoundException extends RuntimeException {
    public PokerPlanningSessionNotFoundException(String id) {
        super(String.format("Session with id = %s is not found", id));
    }
}
