package com.teamgold.goldenharvest.domain.inventory.command.application.controller;

import com.teamgold.goldenharvest.common.response.ApiResponse;
import com.teamgold.goldenharvest.domain.inventory.command.application.dto.DiscardItemRequest;
import com.teamgold.goldenharvest.domain.inventory.command.application.service.DiscardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscardController {

	private final DiscardService discardService;

	@PostMapping("/discard")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> discardItem(
			@RequestBody @Validated DiscardItemRequest discardItemRequest,
			@AuthenticationPrincipal Jwt jwt) {

		return ResponseEntity.ok(ApiResponse.success(discardService.discardItem(discardItemRequest, jwt)));
	}
}
