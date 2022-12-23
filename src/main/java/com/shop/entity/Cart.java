package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
    // 회원 한 명당 1개의 장바구니를 가지므로 처음 장바구니에 상품을 담을 때는 해당 회원의 장바구니를 생성
    // Cart 클래스에서 회원 엔티티를 파라미터로 받아서 장바구니 엔티티를 생성하는 로직을 추가
}
