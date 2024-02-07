package com.dokebi.dalkom.domain.inquiry.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "inquiry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Inquiry extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inquirySeq;

	@ManyToOne
	@JoinColumn(name = "categorySeq")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@ManyToOne
	@JoinColumn(name = "adminSeq")
	private Admin admin;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "answerContent")
	private String answerContent;

	@Column(name = "answerState", nullable = false)
	private String answerState;

	@Column(name = "answeredAt")
	private LocalDateTime answeredAt;

	public Inquiry(Category category, User user, String title, String content, String answerState) {
		this.category = category;
		this.user = user;
		this.title = title;
		this.content = content;
		this.answerState = answerState;
	}

	public Inquiry(Category category, Admin admin, String title, String content, String answerState) {
		this.category = category;
		this.admin = admin;
		this.title = title;
		this.content = content;
		this.answerState = answerState;
	}
}
