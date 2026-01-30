package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.discard.DiscardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscardStatusRepository extends JpaRepository<DiscardStatus, String> {

	Optional<DiscardStatus> findByDiscardStatus(String discardStatus);
}
