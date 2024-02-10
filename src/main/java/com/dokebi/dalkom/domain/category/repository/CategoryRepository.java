package com.dokebi.dalkom.domain.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
import com.dokebi.dalkom.domain.category.dto.SubCategoryResponse;
import com.dokebi.dalkom.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findCategoryByCategorySeq(Long categorySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.category.dto.CategoryResponse("
		+ "c.categorySeq, c.name, c.imageUrl) "
		+ "FROM Category c WHERE c.parentSeq IS NULL AND c.categorySeq < 33 "
		+ "ORDER BY c.categorySeq")
	List<CategoryResponse> findCategoryList();

	@Query("SELECT NEW com.dokebi.dalkom.domain.category.dto.SubCategoryResponse( "
		+ "c.categorySeq, c.name) "
		+ "FROM Category c WHERE c.parentSeq = :categorySeq "
		+ "ORDER BY c.categorySeq")
	List<SubCategoryResponse> findSubCategoryList(@Param("categorySeq") Long categorySeq);
}
