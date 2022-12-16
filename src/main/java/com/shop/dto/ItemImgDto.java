package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
    // ItemImg 엔티티 객체를 파라미터로 받아 ItemImg 객체의 자료형과
    // 멤버 변수의 이름이 같을 때 ItemImgDto 로 값 복사 및 반환
    // static 으로 선언해 ItemImgDto 객체를 생성할 수 있도록 함
}
