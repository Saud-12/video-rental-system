package com.crio.video_rental_system.exception;

public class MaxRentalLimitException extends RuntimeException{
    public MaxRentalLimitException(String message){
        super(message);
    }
}
