package de.tekup.springcloud.couponservice.service;

import de.tekup.springcloud.couponservice.dto.CouponRequestDTO;
import de.tekup.springcloud.couponservice.dto.CouponResponseDTO;
import de.tekup.springcloud.couponservice.entity.Coupon;
import de.tekup.springcloud.couponservice.exception.coupon.CouponServiceBusinessException;
import de.tekup.springcloud.couponservice.exception.coupon.CouponAlreadyExistsException;
import de.tekup.springcloud.couponservice.exception.coupon.CouponNotFoundException;
import de.tekup.springcloud.couponservice.repository.CouponRepository;
import de.tekup.springcloud.couponservice.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CouponService {

    private CouponRepository couponRepository;

    @Cacheable(value = "coupon")
    public List<CouponResponseDTO> getCoupons() throws CouponServiceBusinessException {
        List<CouponResponseDTO> couponResponseDTOS;

        try {
            log.info("CouponService::getCoupons - Fetching Started.");

            List<Coupon> coupons = couponRepository.findAll();

            if (!coupons.isEmpty()) {
                couponResponseDTOS = coupons.stream()
                        .map(ValueMapper::convertToDto)
                        .collect(Collectors.toList());
            } else {
                couponResponseDTOS = Collections.emptyList();
            }

            log.info("CouponService::getCoupons - All coupons are fetched. {}", ValueMapper.jsonToString(couponResponseDTOS));
        } catch (Exception exception) {
            log.error("Exception occurred while retrieving coupons, Exception message {}", exception.getMessage());
            throw new CouponServiceBusinessException("Exception occurred while fetching all coupons");
        }

        log.info("CouponService::getCoupons - Fetching Ends.");
        return couponResponseDTOS;
    }

    @Cacheable(value = "coupon")
    public CouponResponseDTO getCouponById(Long id) throws CouponServiceBusinessException {
        CouponResponseDTO couponResponseDTO;
        try {
            log.info("CouponService::getCouponById - Fetching Started.");

            Coupon coupon = couponRepository.findById(id)
                    .orElseThrow(() -> new CouponNotFoundException("Coupon with ID " + id + " not found"));
            couponResponseDTO = ValueMapper.convertToDto(coupon);

            log.debug("CouponService::getCoupons - Coupon retrieved by ID: {} {}", id, ValueMapper.jsonToString(couponResponseDTO));
        } catch (Exception exception) {
            log.error("Exception occurred while retrieving Coupon, Exception message {}", exception.getMessage());
            throw new CouponServiceBusinessException("Exception occurred while fetching coupon by id");
        }

        log.info("CouponService::getCoupons - Fetching Ends.");
        return couponResponseDTO;
    }

    @Cacheable(value = "coupon")
    public CouponResponseDTO getCouponByCode(String code) throws CouponServiceBusinessException {
        CouponResponseDTO couponResponseDTO = null;
        try {
            log.info("CouponService::getCouponByCode - Fetching Started.");

            Coupon coupon = couponRepository.findByCode(code)
                    .orElseThrow(() -> new CouponNotFoundException("Coupon with code " + code + " not found"));
            couponResponseDTO = ValueMapper.convertToDto(coupon);

            log.debug("CouponService::getCoupons - Coupon retrieved by code: {} {}", code, ValueMapper.jsonToString(couponResponseDTO));
        } catch (Exception exception) {
            log.error("Exception occurred while retrieving Coupon, Exception message {}", exception.getMessage());
            throw new CouponServiceBusinessException("Exception occurred while fetching coupon by code");
        }

        log.info("CouponService::getCouponByCode - Fetching Ends.");
        return couponResponseDTO;
    }

    public CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO couponResponseDTO;

        try {
            log.info("CouponService::createCoupon creating coupon started.");
            // Converted to entity
            Coupon coupon = ValueMapper.convertToEntity(couponRequestDTO);

            Optional<Coupon> existingCoupon = couponRepository.findByCode(couponRequestDTO.getCode());
            if (existingCoupon.isPresent()) {
                throw new CouponAlreadyExistsException("Coupon with code " + couponRequestDTO.getCode() + " already exists");
            }

            // Saving Coupon
            Coupon persistedCoupon = couponRepository.save(coupon);

            // Converting Coupon to DTO response
            couponResponseDTO = ValueMapper.convertToDto(persistedCoupon);
            log.debug("CouponService::createCoupon received response {}", ValueMapper.jsonToString(couponResponseDTO));

        } catch (Exception exception) {
            log.error("Exception occurred while persisting coupon, Exception message {}", exception.getMessage());
            throw new CouponServiceBusinessException("Exception occurred while creating a new coupon");
        }

        log.info("CouponService::createCoupon - Creating coupon with code: {}", couponResponseDTO.getCode());
        return couponResponseDTO;
    }

    public CouponResponseDTO updateCoupon(Long id, CouponRequestDTO updatedCoupon) throws CouponServiceBusinessException {
        CouponResponseDTO couponResponseDTO;

        try {
            log.info("CouponService::updateCoupon - Starts.");
            // Converting Request to Entity
            Coupon coupon = ValueMapper.convertToEntity(updatedCoupon);

            // Checking if the updated coupon exists
            CouponResponseDTO fetchedCoupon = getCouponById(id);
            coupon.setId(id);
            coupon.setCreatedAt(fetchedCoupon.getCreatedAt());

            // Updating coupon
            Coupon persistedCoupon = couponRepository.save(coupon);

            // Converting to Response
            couponResponseDTO = ValueMapper.convertToDto(persistedCoupon);
            log.debug("CouponService::updateCoupon received response {}", ValueMapper.jsonToString(couponResponseDTO));

        } catch (Exception exception) {
            log.error("Exception occurred while updating coupon, Exception message {}", exception.getMessage());
            throw new CouponServiceBusinessException("Exception occurred while updating coupon");
        }

        log.info("CouponService::updateCoupon - Updating coupon with ID: {}", id);
        return couponResponseDTO;
    }

    public void deleteCoupon(Long id) {
        log.info("CouponService::deleteCoupon - Starts.");

        try {
            log.info("CouponService::deleteCoupon - Deleting coupon with ID: {}", id);
            Coupon coupon = couponRepository.findById(id)
                    .orElseThrow(() -> new CouponNotFoundException("Coupon with ID " + id + " not found"));

            couponRepository.delete(coupon);
            log.info("CouponService::deleteCoupon - Deleting coupon with ID: {}", id);
        } catch (Exception exception) {
            log.error("Exception occurred while deleting coupon, Exception message {}", exception.getMessage());
            throw new CouponServiceBusinessException("Exception occurred while deleting a new coupon");
        }
        log.info("CouponService::updateCoupon - Ends.");
    }
}
