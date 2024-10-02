package com.likelion.lionlib.exception;

public class DuplicateMemberException extends RuntimeException{

    public DuplicateMemberException(String email){ super("이미 존재하는 이메일입니다.");}
}