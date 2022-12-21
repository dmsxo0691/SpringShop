package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    // orphanRemoval: 부모가 삭제되었을 때 자식도 함께 삭제하는 역할
    private List<OrderItem> orderItems = new ArrayList<>();

    // order 내부에 orderitems 가 OneToMany 로 양방향으로 선언되어 있기 때문에
    // 양쪽에 값을 넣을 addOrderItem(OrderItem orderItem)
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this); // 양방향 참조 관계이므로 orderItem 객체에도 order 객체 세팅
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member); // 상품을 주문한 회원의 정보를 세팅

        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        // 상품 페이지에서는 1개의 상품을 주문하지만 장바구니 페이지에서는 한 번에 여러개의 상품 주문 가능
        // 따라서 여러 개의 주문 상품을 담을 수 있게 리스트 형태로 값을 받으며 주문 객체에 orderItem 객체를 추가

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
