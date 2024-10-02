package com.likelion.lionlib.exception;

public class DuplicateReservationException extends RuntimeException{
    public DuplicateReservationException() { super("이미 해당 도서를 예약한 상태입니다.");}
}