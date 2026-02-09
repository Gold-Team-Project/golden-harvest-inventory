package com.teamgold.goldenharvest.domain.inventory.query.controller;

import com.teamgold.goldenharvest.common.response.ApiResponse;
import com.teamgold.goldenharvest.domain.inventory.query.service.InventoryQueryService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InventoryQueryController {

	private final InventoryQueryService inventoryQueryService;

	/*
	 * Lot 단위 재고 중 실 판매 가능한
	 * 재고 리스트를 가져올 수 있는 endpoint이다
	 * sku를 명시하여 필터링이 가능하다
	 */
	@GetMapping("/items")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse<?>> getAvailableItemList(
			@RequestParam(name = "page", defaultValue = "1") @Min(1) Integer page,
			@RequestParam(name = "size", defaultValue = "20") @Min(1) @Max(50) Integer size,
			@RequestParam(name = "skuNo", required = false) String skuNo,
			@RequestParam(name = "itemName", required = false) String itemName) {
		return ResponseEntity
				.ok(ApiResponse.success(inventoryQueryService.getAllAvailableItem(page, size, skuNo, itemName)));
	}

	/*
	 * 과거 재고 기록을 포함한 모든 재고를 조회할 수 있는
	 * 관리자 전용 endpoint이다
	 * sku, 날짜, 상태를 통한 필터링이 가능하다
	 */
	@GetMapping("/admin/items")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> getAllItemList(
			@RequestParam(name = "page", defaultValue = "1") @Min(1) Integer page,
			@RequestParam(name = "size", defaultValue = "20") @Min(1) @Max(50) Integer size,
			@RequestParam(name = "itemName", required = false) String itemName,
			@RequestParam(name = "lotNo", required = false) String lotNo,
			@RequestParam(name = "startDate", required = false) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) LocalDate endDate,
			@RequestParam(name = "status", required = false) String status) {
		return ResponseEntity.ok(ApiResponse.success(inventoryQueryService.getAllItem(
				page,
				size,
				itemName,
				lotNo,
				startDate,
				endDate,
				status)));
	}

	/*
	 * 입고(Inbound) 이력을 가져올 수 있는 endpoint이다
	 * startDate와 endDate로 날짜 필터링이 가능하며
	 * 기본값은 현재 시점으로 1주일 사이의 데이터를 가져온다
	 * skuNo를 명시하여 필터링이 가능하다
	 */
	@GetMapping("/inbound")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> getAllInbounds(
			@RequestParam(name = "page", defaultValue = "1") @Min(1) Integer page,
			@RequestParam(name = "size", defaultValue = "20") @Min(1) @Max(50) Integer size,
			@RequestParam(name = "skuNo", required = false) String skuNo,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return ResponseEntity.ok(ApiResponse.success(inventoryQueryService.getInbounds(
				page,
				size,
				skuNo,
				startDate,
				endDate)));
	}

	/*
	 * 출고(Outbound) 이력을 가져올 수 있는 endpoint이다
	 * startDate와 endDate로 날짜 필터링이 가능하며
	 * 기본값은 현재 시점으로 1주일 사이의 데이터를 가져온다
	 * sku를 명시하여 필터링이 가능하다
	 * lot를 명시하여 필터링이 가능하다
	 */
	@GetMapping("/outbound")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> getAllOutbounds(
			@RequestParam(name = "page", defaultValue = "1") @Min(1) Integer page,
			@RequestParam(name = "size", defaultValue = "20") @Min(1) @Max(50) Integer size,
			@RequestParam(name = "skuNo", required = false) String skuNo,
			@RequestParam(name = "lotNo", required = false) String lotNo,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return ResponseEntity.ok(ApiResponse.success(inventoryQueryService.getOutbounds(
				page,
				size,
				skuNo,
				lotNo,
				startDate,
				endDate)));
	}
}
