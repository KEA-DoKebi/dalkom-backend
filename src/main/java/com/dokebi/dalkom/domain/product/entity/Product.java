package com.dokebi.dalkom.domain.product.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productSeq;

	@ManyToOne
	@JoinColumn(name = "categorySeq")
	private Category category;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "info", nullable = false)
	private String info;

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;

	@Column(name = "company", nullable = false)
	private String company;

	@Column(name = "state", nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
	private String state;

    @OneToMany(mappedBy = "product")
    private List<ProductStock> productStock = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImage = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> ordrDetail = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<OrderCart> OrderCart = new ArrayList<>();

}