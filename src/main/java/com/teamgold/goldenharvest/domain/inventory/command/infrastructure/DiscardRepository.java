package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.discard.Discard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscardRepository extends JpaRepository<Discard, String> {
}
