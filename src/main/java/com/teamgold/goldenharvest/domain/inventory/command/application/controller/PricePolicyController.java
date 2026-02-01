package com.teamgold.goldenharvest.domain.inventory.command.application.controller;

import com.teamgold.goldenharvest.common.response.ApiResponse;
import com.teamgold.goldenharvest.domain.inventory.command.application.dto.PricePolicyRequest;
import com.teamgold.goldenharvest.domain.inventory.command.application.dto.PricePolicyUpdateRequest;
import com.teamgold.goldenharvest.domain.inventory.command.application.service.PricePolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class PricePolicyController {

	private final PricePolicyService pricePolicyService;

	@PostMapping("/price-policy")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> registerPricePolicy(@Valid @RequestBody PricePolicyRequest pricePolicyRequest) {
		return ResponseEntity.ok(ApiResponse.success(pricePolicyService.registerPricePolicy(pricePolicyRequest)));
	}

	@PatchMapping("/price-policy")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> updatePricePolicy(@Valid @RequestBody PricePolicyUpdateRequest pricePolicyUpdateRequest) {
		return ResponseEntity.ok(ApiResponse.success(pricePolicyService.updatePricePolicy(pricePolicyUpdateRequest)));
	}
}
