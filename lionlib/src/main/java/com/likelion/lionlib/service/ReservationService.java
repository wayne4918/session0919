package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.*;

import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.request.ReservationRequest;
import com.likelion.lionlib.dto.response.ReservationResponse;
import com.likelion.lionlib.dto.response.ReserveCountResponse;
import com.likelion.lionlib.exception.DuplicateMemberException;
import com.likelion.lionlib.exception.DuplicateReservationException;
import com.likelion.lionlib.exception.ReservationNotFoundException;
import com.likelion.lionlib.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GlobalService globalService;

    public ReservationService(ReservationRepository reservationRepository, GlobalService globalService){
        this.reservationRepository = reservationRepository;
        this.globalService = globalService;
    }

    @Transactional
    public ReservationResponse addReserve(CustomUserDetails customUserDetails, ReservationRequest reservationRequest){
        Member member = globalService.findMemberById(customUserDetails.getId());
        Book book = globalService.findBookById(reservationRequest.getBookId());

        reservationRepository.findByMemberAndBook(member, book).ifPresent(
                reservation -> {throw new DuplicateReservationException();}
        );

        Reservation savedReservation = Reservation.builder()
                .member(member)
                .book(book)
                .build();
        reservationRepository.save(savedReservation);
        return ReservationResponse.fromEntity(savedReservation);
    }

    public ReservationResponse getReserve(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        return ReservationResponse.fromEntity(reservation);
    }

    @Transactional
    public void deleteReserve(Long reservationId){
        reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationResponse> getMemberReserve(CustomUserDetails customUserDetails){
        Member member = globalService.findMemberById(customUserDetails.getId());
        List<Reservation> reserveList = reservationRepository.findByMember(member);

        return reserveList.stream().map(ReservationResponse::fromEntity).toList();
    }

    public ReserveCountResponse countReserve(Long bookId){
        Book book = globalService.findBookById(bookId);

        Long count = reservationRepository.countAllByBook(book);

        return new ReserveCountResponse(count);
    }


}