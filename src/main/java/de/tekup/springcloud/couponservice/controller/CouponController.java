package de.tekup.springcloud.couponservice.controller;

import de.tekup.springcloud.couponservice.dto.APIResponse;
import de.tekup.springcloud.couponservice.dto.CouponRequestDTO;
import de.tekup.springcloud.couponservice.dto.CouponResponseDTO;
import de.tekup.springcloud.couponservice.entity.Coupon;
import de.tekup.springcloud.couponservice.service.CouponService;
import de.tekup.springcloud.couponservice.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/coupons")
@Slf4j
public class CouponController {
    public static final String SUCCESS = "SUCCESS";
    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllCoupons() {
        List<CouponResponseDTO> coupons = couponService.getCoupons();

        // Builder Design pattern (to avoid complex object creation)
        APIResponse<List<CouponResponseDTO>> responseDTO = APIResponse
                .<List<CouponResponseDTO>>builder()
                .status(SUCCESS)
                .results(coupons)
                .build();

        log.info("CouponController::getAllCoupons response {}", ValueMapper.jsonToString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCouponById(@PathVariable Long id) {
        log.info("CouponController::getCouponById {}", id);
        CouponResponseDTO couponResponseDTO = couponService.getCouponById(id);

        APIResponse<CouponResponseDTO> responseDTO = APIResponse
                .<CouponResponseDTO>builder()
                .status(SUCCESS)
                .results(couponResponseDTO)
                .build();

        log.info("CouponController::getCouponById {} response {}", id, ValueMapper.jsonToString(couponResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<APIResponse> getCouponByCode(@PathVariable String code) {
        log.info("CouponController::getCouponByCode {}", code);
        CouponResponseDTO couponResponseDTO = couponService.getCouponByCode(code);

        APIResponse<CouponResponseDTO> responseDTO = APIResponse
                .<CouponResponseDTO>builder()
                .status(SUCCESS)
                .results(couponResponseDTO)
                .build();

        log.info("CouponController::getCouponByCode {} response {}", code, ValueMapper.jsonToString(couponResponseDTO));

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createCoupon(@RequestBody @Valid CouponRequestDTO couponRequestDTO) {
        log.info("CouponController::createCoupon request body {}", ValueMapper.jsonToString(couponRequestDTO));

        CouponResponseDTO createdCoupon = couponService.createCoupon(couponRequestDTO);

        APIResponse<CouponResponseDTO> responseDTO = APIResponse
                .<CouponResponseDTO>builder()
                .status(SUCCESS)
                .results(createdCoupon)
                .build();

        log.info("ProductController::createNewProduct response {}", ValueMapper.jsonToString(responseDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponRequestDTO couponRequestDTO
    ) {
        log.info("CouponController::updateCoupon request body {}", ValueMapper.jsonToString(couponRequestDTO));

        CouponResponseDTO updatedCoupon = couponService.updateCoupon(id, couponRequestDTO);

        APIResponse<CouponResponseDTO> responseDTO = APIResponse
                .<CouponResponseDTO>builder()
                .status(SUCCESS)
                .results(updatedCoupon)
                .build();

        log.info("ProductController::updateCoupon response {}", ValueMapper.jsonToString(responseDTO));
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
