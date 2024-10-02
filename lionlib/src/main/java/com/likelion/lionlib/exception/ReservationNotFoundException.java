package com.likelion.lionlib.exception;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(Long Id){ super("해당 예약을 찾을 수 없습니다."); }
}