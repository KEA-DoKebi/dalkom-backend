package com.dokebi.dalkom.domain.inqury.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.admin.entity.admin;
import com.dokebi.dalkom.domain.category.entity.category;
import com.dokebi.dalkom.domain.user.entitiy.user;
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
public class inquiry extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquirySeq;

    @ManyToOne
    @JoinColumn(name = "categorySeq")
    private category category;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private user user;

    @ManyToOne
    @JoinColumn(name = "adminSeq")
    private admin admin;

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
