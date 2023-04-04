package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import com.shop.dto.ItemFormDto;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //상품 코드, pk

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name = "price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob //Large Objcet -> 큰 문자열이나 큰 객체 저장 가능, column 대신 Lob 사용시 길이 제한 없이 저장 가능! (상세설명은 길이가 기니까)
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    // 상품 찜하기 기능을 위한 필드
    private boolean liked;

    @Enumerated(EnumType.STRING) //Enum 타입의 필드 매핑시 사용, Enum의 이름을 db에 저장, 즉 판매, 품절, 판매중지 등 상태를 db에 저장
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    public void updateItem(ItemFormDto itemFormDto) { //업데이트
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) { //매개변수로 받은 수량 만큼 상품의 재고 감소, 감소된 후 재고 수량이 0보다 작으면 오류!
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock; //예외가 발생하지 않으면, restStock 값을 Item 객체의 stockNumber 필드에 저장
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    } //매개변수로 받은 수량만큼 재고 증가 시키기

}