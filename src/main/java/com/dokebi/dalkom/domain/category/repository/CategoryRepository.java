package com.dokebi.dalkom.domain.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
import com.dokebi.dalkom.domain.category.dto.SubCategoryResponse;
import com.dokebi.dalkom.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("SELECT c.categorySeq, c.name, c.imageUrl FROM category c WHERE c.parentSeq IS NULL")
	List<CategoryResponse> getCategoryList();

	@Query("SELECT c.categorySeq, c.name FROM category c WHERE c.parentSeq = :categorySeq")
	List<SubCategoryResponse> getSubCategoryList(@Param("categorySeq") Long categorySeq);
}
