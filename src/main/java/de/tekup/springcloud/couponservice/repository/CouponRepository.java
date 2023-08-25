package de.tekup.springcloud.couponservice.repository;

import de.tekup.springcloud.couponservice.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByCode(String code);
}