package com.teamgold.goldenharvest.domain.inventory.command.infrastructure;

import com.teamgold.goldenharvest.domain.inventory.command.domain.mirror.ItemMasterMirror;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMasterMirrorRepository extends JpaRepository<ItemMasterMirror, String> {
	List<ItemMasterMirror> findBySkuNo(String skuNo);
}
