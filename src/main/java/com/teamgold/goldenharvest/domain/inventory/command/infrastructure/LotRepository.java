package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.Lot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Lot> findBySkuNoAndLotStatusOrderByInboundDateAsc(String skuNo, Lot.LotStatus lotStatus);

	@Lock(LockModeType.OPTIMISTIC)
	List<Lot> findByLotNo(String lotNo);
}
