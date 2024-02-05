package com.coupon.system.api.service;

import org.springframework.stereotype.Service;

import com.coupon.system.api.producer.CouponCreateProducer;
import com.coupon.system.api.repository.AppliedUserRepository;
import com.coupon.system.api.repository.CouponCountRepository;
import com.coupon.system.api.repository.CouponRepository;

@Service
public class ApplyService {
	
	private final CouponRepository couponRepository;
	
//	public ApplyService(CouponRepository couponRepository) {
//		this.couponRepository = couponRepository;
//	}
	
//	// 쿠폰 발급 메소드
//	public void apply(Long userId) {
//		// 1. 쿠폰의 개수를 가져옴
//		long count = couponRepository.count(); // MySQL에서 쿠폰의 개수를 가져오는 로직
//		
//		// 2. 쿠폰의 개수가 발급 가능한 개수가 초과되었을 경우에는 발급하지 않음
//		if(count > 100) {
//			return;
//		}
//		
//		// 3. 아직 발급이 가능한 경우에는 쿠폰을 새로 생성
//		couponRepository.save(new Coupon(userId)); // 직접 쿠폰을 생성하는 로직
//	}
	
	// 추가
	private final CouponCountRepository couponCountRepository; 
	
//	public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository) {
//		this.couponRepository = couponRepository;
//		this.couponCountRepository = couponCountRepository;
//	}
//	
//	// 쿠폰 발급 메소드
//	public void apply(Long userId) {
//		// 1. 쿠폰의 개수를 가져옴
//		// long count = couponRepository.count(); // MySQL에서 쿠폰의 개수를 가져오는 로직
//		Long count = couponCountRepository.increment(); // CouponCountRepository의 increment 메소드로 대체
//		
//		// 2. 쿠폰의 개수가 발급 가능한 개수가 초과되었을 경우에는 발급하지 않음
//		if(count > 100) {
//			return;
//		}
//		
//		// 3. 아직 발급이 가능한 경우에는 쿠폰을 새로 생성
//		couponRepository.save(new Coupon(userId)); // 직접 쿠폰을 생성하는 로직
//	}
	
	// 추추가
	private final CouponCreateProducer couponCreateProducer;

//	public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
//		this.couponRepository = couponRepository;
//		this.couponCountRepository = couponCountRepository;
//		this.couponCreateProducer = couponCreateProducer;
//	}
//	
//	// 쿠폰 발급 메소드
//	public void apply(Long userId) {
//		// 1. 쿠폰의 개수를 가져옴
//		// long count = couponRepository.count(); // MySQL에서 쿠폰의 개수를 가져오는 로직
//		Long count = couponCountRepository.increment(); // CouponCountRepository의 increment 메소드로 대체
//		
//		// 2. 쿠폰의 개수가 발급 가능한 개수가 초과되었을 경우에는 발급하지 않음
//		if(count > 100) {
//			return;
//		}
//		
//		// 3. 아직 발급이 가능한 경우에는 쿠폰을 새로 생성
//		// couponRepository.save(new Coupon(userId)); // 직접 쿠폰을 생성하는 로직
//		couponCreateProducer.create(userId); // CouponCreateProducer를 사용해서 Topic에 유저의 아이디를 전송하도록 변경
//	}
	
	// 추추추가
	private final AppliedUserRepository appliedUserRepository; 
	
	public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
		this.couponRepository = couponRepository;
		this.couponCountRepository = couponCountRepository;
		this.couponCreateProducer = couponCreateProducer;
		this.appliedUserRepository = appliedUserRepository;
	}
	
	// 쿠폰 발급 메소드
	public void apply(Long userId) {
		Long apply = appliedUserRepository.add(userId); // 유저가 쿠폰 발급 요청을 했을 때 AppliedUserRepository에 유저 아이디를 추가
		
		if(apply!= 1) { // 만약 추가된 개수가 1이 아니라면 이 유저는 이미 발급 요청을 했던 유저이므로 쿠폰을 발급받았던 발급받지 못했던 결과는 동일하게 쿠폰 발급이 안될 것이므로 쿠폰을 발급하지 않고 리턴
			return; 
		}
		
		// 1. 쿠폰의 개수를 가져옴
		// long count = couponRepository.count();
		Long count = couponCountRepository.increment(); // CouponCountRepository의 increment 메소드로 대체
		
		// 2. 쿠폰의 개수가 발급 가능한 개수가 초과되었을 경우에는 발급하지 않음
		if(count > 100) {
			return;
		}
		
		// 3. 아직 발급이 가능한 경우에는 쿠폰을 새로 생성
		// couponRepository.save(new Coupon(userId)); // 직접 쿠폰을 생성하는 로직
		couponCreateProducer.create(userId); // CouponCreateProducer를 사용해서 Topic에 유저의 아이디를 전송하도록 변경
	}
}
