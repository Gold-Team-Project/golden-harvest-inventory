package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.PricePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricePolicyRepository extends JpaRepository<PricePolicy, String> {
	List<PricePolicy> findBySkuNo(String skuNo);
}
