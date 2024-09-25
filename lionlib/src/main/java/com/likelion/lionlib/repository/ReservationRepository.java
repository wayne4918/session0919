package com.likelion.lionlib.repository;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByMemberAndBook(Member member, Book book);

    List<Reservation> findByMember(Member member);

    Long countAllByBook(Book book);

}