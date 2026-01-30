package com.teamgold.goldenharvest.domain.inventory.query.mapper;

import com.teamgold.goldenharvest.domain.inventory.query.dto.PricePolicyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PricePolicyMapper {

	@Select("""
		SELECT p.sku_no AS skuNo,
		       p.margin_rate AS marginRate,
		       p.is_active AS isActive
		FROM tb_price_policy p
	""")
	List<PricePolicyResponse> findAllPricePolicies();
}
