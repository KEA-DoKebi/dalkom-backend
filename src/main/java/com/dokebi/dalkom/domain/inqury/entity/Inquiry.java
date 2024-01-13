package com.dokebi.dalkom.domain.inqury.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.user.entitiy.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
