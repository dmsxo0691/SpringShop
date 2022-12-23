package com.shop.service;

import com.shop.dto.CartItemDto;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Cart;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.CartItemRepository;
import com.shop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId())
                // 장바구니에 담을 상품 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        // 로그인한 회원 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        // 로그인한 회원의 장바구니 조회
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        // 상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티 생성

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        // 현재 상품이 장바구니에 들어가 있는지 조회
        if (savedCartItem != null) { // 있다면 수량 추가
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else { // 없다면 장바구니에 해당 상품 목록 생성
            CartItem cartItem = CartItem.craeteCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
