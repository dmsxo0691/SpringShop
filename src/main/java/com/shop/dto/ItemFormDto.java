package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemFormDto> itemFormDtoList = new ArrayList<>();

    private List<Long> itemImgIds;

    private static ModelMapper modelMapper = new ModelMapper();

    public ItemFormDto() {
        itemImgIds = new ArrayList<>();
    }

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
    // ItemFormDto -> Item 엔티티 연결
    // ItemFormDto 를 Item.class 로 변경해주는 코드, 동일한 필드명 기준 item 객체로 변환

    // 수정 시 정보를 가져올 수 있음
    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
    // of 메소드는 item 객체를 받아 반대로 ItemFormDto 객체를 반환
    // item 엔티티 -> ItemFormDto 연결
}
