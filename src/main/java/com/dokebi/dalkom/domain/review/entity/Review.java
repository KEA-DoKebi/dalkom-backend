package com.dokebi.dalkom.domain.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewSeq;

	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@OneToOne
	@JoinColumn(name = "orderDetailSeq")
	private OrderDetail orderDetail;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "rating", nullable = false)
	private Integer rating;

	@Builder
	public Review(User user, OrderDetail orderDetail, String content, Integer rating) {
		this.user = user;
		this.orderDetail = orderDetail;
		this.content = content;
		this.rating = rating;
	}
}
