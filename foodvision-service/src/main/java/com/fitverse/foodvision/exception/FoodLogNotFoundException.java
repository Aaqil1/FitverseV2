package com.fitverse.foodvision.exception;

public class FoodLogNotFoundException extends RuntimeException {

    public FoodLogNotFoundException(Long id) {
        super("Food log not found for id %d".formatted(id));
    }
}
