package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    //쿼리 메소드
    //스프링 데이터 jpa 에서 제공
    //Repository 인터페이스에서 간단한 네이밍 룰을 이용하여 메소드를 작성하면
    //원하는 쿼리 실행 가능

    //엔티티 이름은 생략 가능, by 뒤에는 검색할 때 사용할 변수 이름
    //find + (엔티티 이름) + By + 변수 이름

    List<Item> findByItemName(String itemName);

    List<Item> findByItemNameOrItemDetail(String ItemName, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like " + "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

//    @Query(value = "select * from item i where i.item_detail like " +
//            "%:itemDetail% order by i.price desc", nativeQuery = true)
//    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
