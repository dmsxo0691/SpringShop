package com.shop.exception;

public class OutOfStockException extends RuntimeException {
    // 상품 주문 수량보다 재고의 수가 적을 때 발생시킬 exception
    public OutOfStockException(String message) {
        super(message);
        // 기존의 런타임 예외처리를 상속받아 내가 원하는 메세지를 출력하기 위한 상속
    }
}
