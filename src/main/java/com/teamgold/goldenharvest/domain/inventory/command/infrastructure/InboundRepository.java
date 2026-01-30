package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InboundRepository extends JpaRepository<Inbound, String> {

	Optional<Inbound> findByPurchaseOrderItemId(String purchaseOrderItemId);
}