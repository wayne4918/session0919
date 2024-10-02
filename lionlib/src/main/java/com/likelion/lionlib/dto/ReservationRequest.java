package com.likelion.lionlib.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode를 포함
@NoArgsConstructor
@AllArgsConstructor // 추가: 모든 필드를 인자로 받는 생성자
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationRequest {

    private Long memberId;
    private Long bookId;

    // 생성자 추가
    public ReservationRequest(Long memberId) {
        this.memberId = memberId;
    }

    // 필드 검증 메서드 추가 (예시)
    public boolean isValid() {
        return memberId != null && bookId != null;
    }
}