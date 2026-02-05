package com.teamgold.goldenharvest.domain.inventory.query.mapper;

import com.teamgold.goldenharvest.domain.inventory.query.dto.MetricsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MetricsMapper {

    @Select("""
    SELECT
        (SELECT IFNULL(SUM(quantity), 0) FROM tb_lot WHERE lot_status = 'AVAILABLE') AS availableQuantity,
        (SELECT COUNT(*) FROM tb_lot WHERE lot_status = 'AVAILABLE') AS availableLot,
        (SELECT COUNT(*) FROM tb_inbound WHERE inbound_date = CURDATE()) AS inbounds,
        (SELECT COUNT(*) FROM tb_outbound WHERE outbound_date = CURDATE()) AS outbounds,
        (SELECT COUNT(*) FROM tb_lot WHERE inbound_date > DATE_SUB(CURDATE(), INTERVAL 7 DAY)) AS expireDueLot,
        (SELECT IFNULL(SUM(quantity), 0) FROM tb_discard
         WHERE discarded_at >= DATE_FORMAT(NOW(), '%Y-%m-01')
           AND discarded_at < DATE_FORMAT(NOW() + INTERVAL 1 MONTH, '%Y-%m-01')
        ) AS discardQuantity
    """)
    MetricsResponse getMetrics();
}
