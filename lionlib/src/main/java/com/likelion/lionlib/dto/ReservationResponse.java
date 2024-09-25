package com.likelion.lionlib.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.lionlib.domain.Reservation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 추가
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationResponse {

    private Long memberId;
    private Long bookId;

    // 정적 팩토리 메서드 변형
    public static ReservationResponse from(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.memberId = reservation.getMember().getId();
        response.bookId = reservation.getBook().getId();
        return response;
    }

    // 커스텀 생성자 추가 (예시)
    public ReservationResponse(Long memberId, Long bookId) {
        this.memberId = memberId;
        this.bookId = bookId;
    }
}
