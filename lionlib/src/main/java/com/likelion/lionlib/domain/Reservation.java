package com.likelion.lionlib.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter // 추가: 필요한 경우에만 setter 사용 가능
@ToString(exclude = {"member", "book"}) // 추가: 순환 참조 방지
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // nullable 대신 optional 사용
    @JoinColumn(name = "member_id", nullable = false) // 명시적으로 nullable 설정
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


    public void assignMember(Member member) {
        this.member = member;
    }

    public void assignBook(Book book) {
        this.book = book;
    }
}
