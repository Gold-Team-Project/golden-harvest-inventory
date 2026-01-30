package com.teamgold.goldenharvest.domain.inventory.query.service;

import com.teamgold.goldenharvest.domain.inventory.query.dto.PricePolicyResponse;
import com.teamgold.goldenharvest.domain.inventory.query.mapper.PricePolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PricePolicyQueryService {

	private final PricePolicyMapper pricePolicyMapper;

	public List<PricePolicyResponse> getAllPricePolicy() {
		return pricePolicyMapper.findAllPricePolicies();
	}
}
