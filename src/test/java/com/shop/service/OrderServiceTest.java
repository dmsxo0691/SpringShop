package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.constant.OrderStatus;
import com.shop.dto.OrderDto;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem(){ // 테스트를 위한 상품 생성 메소드
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){ // 테스트를 위한 회원 정보를 저장하는 메소드
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);

    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, member.getEmail());
        // 주문 로직 호출 결과 생성된 주문 번호를 orderId 변수에 저장
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // 주문 번호를 이용하여 저장된 주문 정보를 조회
        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount()*item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }

//    @Test
//    @DisplayName("주문 취소 테스트")
//    public void cancelOrder(){
//        Item item = saveItem();
//        Member member = saveMember();
//
//        OrderDto orderDto = new OrderDto();
//        orderDto.setCount(10);
//        orderDto.setItemId(item.getId());
//        Long orderId = orderService.order(orderDto, member.getEmail());
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(EntityNotFoundException::new);
//        orderService.cancelOrder(orderId);
//
//        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
//        assertEquals(100, item.getStockNumber());
//    }

}