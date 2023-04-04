package com.shop.exception;

public class OutOfStockException extends RuntimeException{ //주문 수량보다 재고의 수가 적을때 exception

    public OutOfStockException(String message){
        super(message);

}
}
