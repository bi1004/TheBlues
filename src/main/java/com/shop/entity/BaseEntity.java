package com.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class}) //BaseEntity에서 엔티티 이벤트를 처리하기 위한 리스너 클래스를 정의
@MappedSuperclass //하위 엔티티에게 공통으로 사용되는 필드들을 정의
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy //엔티티가 생성되어 저장될 때 시간을 자동으로 저장
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy //엔티티의 값을 변경할 때 시간을 자동으로 저장
    private String modifiedBy;

}