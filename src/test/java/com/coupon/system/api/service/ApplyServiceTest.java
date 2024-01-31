package com.coupon.system.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coupon.system.api.repository.CouponRepository;

@SpringBootTest
public class ApplyServiceTest {

	@Autowired
	private ApplyService applyService;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Test
	public void 한번만응모() {
		applyService.apply(1L);
		
		long count = couponRepository.count();
		
		System.out.println("count : " + count);
		assertThat(count).isEqualTo(1);
	}
	
	@Test
	public void 여러명응모() throws InterruptedException {
		int threadCount = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		
		for(int i=0; i<threadCount; i++) {
			long userId = i;
			executorService.submit(() -> {
				try {
					applyService.apply(userId);
				} finally {
					latch.countDown();
				}
			});
		}
		
		latch.await();
		
		Thread.sleep(10000); // Consumer에서 쿠폰을 생성할 때 쿠폰이 100개가 정상적으로 생성되는지 확인을 하기 위해서 일시적으로 Thread.sleep()을 활용
		
		long count = couponRepository.count();

		System.out.println("count : " + count);
		assertThat(count).isEqualTo(100);
	}
}
