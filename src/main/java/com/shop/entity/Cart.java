package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "cart") //table 이름 cart
@Getter @Setter
@ToString
public class Cart extends BaseEntity { //BaseEntity 클래스 상속

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //pk

    @OneToOne(fetch = FetchType.LAZY) //필요할 때까지 로딩x
    @JoinColumn(name="member_id")
    private Member member;

    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }

}