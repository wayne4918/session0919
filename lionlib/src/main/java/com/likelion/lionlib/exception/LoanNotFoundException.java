package com.likelion.lionlib.exception;

public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException(Long loanID){ super("해당 Loan을 찾을 수 없습니다.");}
}