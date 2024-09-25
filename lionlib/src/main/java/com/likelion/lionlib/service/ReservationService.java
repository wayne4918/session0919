package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.*;

import com.likelion.lionlib.dto.request.ReservationRequest;
import com.likelion.lionlib.dto.response.ReservationResponse;
import com.likelion.lionlib.dto.response.ReserveCountResponse;
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
    public ReservationResponse addReserve(ReservationRequest reservationRequest){
        Member member = globalService.findMemberById(reservationRequest.getMemberId());
        Book book = globalService.findBookById(reservationRequest.getBookId());

        reservationRepository.findByMemberAndBook(member, book).ifPresent(
                reservation -> {throw new RuntimeException("이미 해당 도서를 예약한 상태입니다.");}
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
                .orElseThrow(() -> new RuntimeException("해당 예약을 찾지 못했습니다."));

        return ReservationResponse.fromEntity(reservation);
    }

    @Transactional
    public void deleteReserve(Long reservationId){
        reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("해당 예약을 찾지 못했습니다."));

        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationResponse> getMemberReserve(Long memberId){
        Member member = globalService.findMemberById(memberId);
        List<Reservation> reserveList = reservationRepository.findByMember(member);

        return reserveList.stream().map(ReservationResponse::fromEntity).toList();
    }

    public ReserveCountResponse countReserve(Long bookId){
        Book book = globalService.findBookById(bookId);

        Long count = reservationRepository.countAllByBook(book);

        return new ReserveCountResponse(count);
    }


}