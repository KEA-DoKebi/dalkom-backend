package com.dokebi.dalkom.domain.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.admin.dto.MonthlyCategoryListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyPriceListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyProductListDto;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;

import io.lettuce.core.dynamic.annotation.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	boolean existsByNickname(String nickname);

	Optional<Admin> findByAdminId(String adminId);

	Optional<Admin> findByAdminSeq(Long adminSeq);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) FROM Admin a")
	Page<ReadAdminResponse> findAllAdminList(Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse(" +
		"a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) " +
		"FROM Admin a WHERE a.name LIKE CONCAT('%', :name, '%')")
	Page<ReadAdminResponse> findAdminListByName(
		@Param("name") String name,
		Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) "
		+ "FROM Admin a WHERE (a.adminId LIKE CONCAT('%', :adminId, '%')) ")
	Page<ReadAdminResponse> findAdminListByAdminId(@Param("adminId") String adminId, Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) "
		+ "FROM Admin a WHERE (a.depart LIKE CONCAT('%', :depart, '%')) ")
	Page<ReadAdminResponse> findAdminListByDepart(@Param("depart") String depart, Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) "
		+ "FROM Admin a WHERE (a.nickname LIKE CONCAT('%', :nickname, '%')) ")
	Page<ReadAdminResponse> findAdminListByNickname(@Param("nickname") String nickname, Pageable pageable);

	@Query("SELECT sum(o.totalPrice) FROM Order o")
	Integer findTotalPrice();

	@Query("SELECT sum(o.totalPrice) FROM Order o "
		+ "WHERE DATE_FORMAT(o.createdAt, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m')")
	Integer findTotalMonthlyPrice();

	@Query("SELECT sum(o.totalPrice) FROM Order o "
		+ "WHERE DATE_FORMAT(o.createdAt, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d')")
	Integer findTotalDailyPrice();

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.MonthlyPriceListDto("
		+ "DATE_FORMAT(o.createdAt, '%Y-%m'), SUM(o.totalPrice)) "
		+ "FROM Order o "
		+ "GROUP BY DATE_FORMAT(o.createdAt, '%Y-%m')")
	List<MonthlyPriceListDto> findMonthlyPriceList();

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.MonthlyProductListDto("
		+ " DATE_FORMAT(od.createdAt, '%Y-%m'), p.productSeq, max(p.name), max(p.company), "
		+ " max(p.price), COUNT(*), sum(od.amount), (sum(od.amount) * p.price)) "
		+ " FROM OrderDetail od LEFT JOIN od.product p "
		+ " WHERE DATE_FORMAT(od.createdAt, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') "
		+ " GROUP by DATE_FORMAT(od.createdAt, '%Y-%m') , p.productSeq "
		+ " ORDER by sum(od.amount) DESC ")
	Page<MonthlyProductListDto> findMonthlyProductList(Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.MonthlyCategoryListDto("
		+ "c.parentSeq, MAX(c2.name),  SUM(od.amount)) "
		+ "from OrderDetail od "
		+ "left join od.product p"
		+ "LEFT JOIN od.product.category c "
		+ "LEFT JOIN Category c2 "
		+ "on c.parentSeq = c2.categorySeq "
		+ "WHERE DATE_FORMAT(od.createdAt, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') "
		+ "GROUP by c.parentSeq")
	List<MonthlyCategoryListDto> findMonthlyCategoryList();
}
