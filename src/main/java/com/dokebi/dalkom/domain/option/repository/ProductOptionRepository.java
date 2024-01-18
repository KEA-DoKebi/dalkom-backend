package com.dokebi.dalkom.domain.option.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.option.dto.OptionCodeResponse;
import com.dokebi.dalkom.domain.option.entity.ProductOption;

import io.lettuce.core.dynamic.annotation.Param;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	@Query("SELECT DISTINCT NEW com.dokebi.dalkom.domain.option.dto.OptionCodeResponse(po.optionCode, po.name) "
		+ "FROM ProductOption po")
	List<OptionCodeResponse> findAllOptionCode();

	@Query("SELECT po.detail FROM ProductOption po WHERE po.optionCode =:optionCode")
	List<String> findDetailByOptionCode(@Param("optionCode") String optionCode);

	ProductOption findByPrdtOptionSeq(Long prdtOptionSeq);
}
