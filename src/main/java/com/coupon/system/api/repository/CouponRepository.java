package com.coupon.system.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupon.system.api.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
