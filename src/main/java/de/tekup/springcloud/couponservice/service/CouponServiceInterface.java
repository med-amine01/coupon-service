package de.tekup.springcloud.couponservice.service;

import de.tekup.springcloud.couponservice.dto.CouponRequestDTO;
import de.tekup.springcloud.couponservice.dto.CouponResponseDTO;
import de.tekup.springcloud.couponservice.exception.CouponServiceBusinessException;

import java.util.List;

public interface CouponServiceInterface {
    List<CouponResponseDTO> getCoupons() throws CouponServiceBusinessException;

    CouponResponseDTO getCouponById(Long id) throws CouponServiceBusinessException;

    CouponResponseDTO getCouponByCode(String code) throws CouponServiceBusinessException;

    CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO);

    CouponResponseDTO updateCoupon(Long id, CouponRequestDTO updatedCoupon) throws CouponServiceBusinessException;

    void deleteCoupon(Long id);
}