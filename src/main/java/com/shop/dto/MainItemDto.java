package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private int price;

    // Projection : 테이블에서 원하는 컬럼만 뽑아서 조회

    // 생성자에 @QueryProjection 를 붙이면 Querydsl 로 결과를 조회 시
    // MainItemDto 객체로 바로 받아오도록 활용
    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, int price) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
