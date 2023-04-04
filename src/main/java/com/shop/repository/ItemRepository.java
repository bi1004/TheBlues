package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>, // JpaRepository 상속 받는 레포지토리, <엔티티 타입 클래스, 기본키 타입>
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findByItemNm(String itemNm); // find + (엔티티 이름) + By +  변수이름 -> 상품 이름을 이용해 데이터조회!

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); //itemNm 필드 또는 itemDetail 중 매개변수로 받은 문자열과 일치하는 값을 찾아 리스트로 반환

    List<Item> findByPriceLessThan(Integer price); //파라미터로 넘어온 price보다 값이 작은 상품 데이터 조회

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price); //가격이 높은 순

    // 상품 찜하기 기능을 위한 메서드
    List<Item> findByLiked(boolean liked);

    @Query("select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    // 일부 문자열이 포함된 항목을 검색하며, 해당 항목들을 가격이 높은 순서대로 정렬하여 반환

    @Query(value="select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}