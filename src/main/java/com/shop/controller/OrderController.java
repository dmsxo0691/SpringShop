package com.shop.controller;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 스프링에서 비동기 처리 할 시
// @RequestBody : Http 요청의 본문 body 에 담긴 내용을 자바 객체로 전달
// @ResponseBody : 자바 객체를 Http 요청의 body 로 전달
    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto
            , BindingResult bindingResult, Principal principal) {

        // 주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는지 검사
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        // 현재 로그인 유저의 정보를 얻기 위해 @Controller 어노테이션이 선언된 클래스에서 메소드 인자로
        // principal 객체로 넘겨 줄 경우 해당 객체에 직접 접근할 수 있음
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);
            // 화면으로부터 넘어오는 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
        // 결과값으로 생성된 주문번호와 요청이 성공했다는 Http 응답 상태 코드를 반환
    }

    @GetMapping(value = {"/orders", "/orders{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }

}
