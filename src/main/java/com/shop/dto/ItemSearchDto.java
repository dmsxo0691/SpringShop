package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    // all : 상품 등록 전체
    // 1d : 최근 하루 동안 등록된 상품
    // 1w : 최근 한 주 동안 등록된 상품
    // 1m : 최근 한 달 동안 등록된 상품
    // 6m : 최근 반년 동안 등록된 상품
    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";
}
