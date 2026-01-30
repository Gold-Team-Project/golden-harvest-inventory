package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.Outbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundRepository extends JpaRepository<Outbound, String> { }
