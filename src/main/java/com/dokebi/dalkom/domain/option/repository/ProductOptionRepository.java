package com.dokebi.dalkom.domain.option.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.option.dto.OptionCodeResponse;
import com.dokebi.dalkom.domain.option.dto.OptionDetailListResponse;
import com.dokebi.dalkom.domain.option.entity.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	@Query("SELECT DISTINCT NEW com.dokebi.dalkom.domain.option.dto.OptionCodeResponse(po.optionCode, po.name) "
		+ "FROM ProductOption po")
	List<OptionCodeResponse> findAllOptionCode();

	@Query("SELECT NEW com.dokebi.dalkom.domain.option.dto.OptionDetailListResponse("
		+ "po.prdtOptionSeq, po.detail) "
		+ "FROM ProductOption po WHERE po.optionCode =:optionCode")
	List<OptionDetailListResponse> findDetailByOptionCode(@Param("optionCode") String optionCode);

	Optional<ProductOption> findProductOptionByPrdtOptionSeq(Long prdtOptionSeq);

	@Query("SELECT po.detail FROM ProductOption po WHERE po.prdtOptionSeq =:prdtOptionSeq")
	String findDetailByPrdtOptionSeq(@Param("prdtOptionSeq") Long prdtOptionSeq);

}
