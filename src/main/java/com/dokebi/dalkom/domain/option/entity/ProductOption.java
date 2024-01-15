package com.dokebi.dalkom.domain.option.entity;

import com.dokebi.dalkom.common.EntityDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "prdtOption")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOptionSeq;

    @Column(name = "optionCode", nullable = false)
    private String optionCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "detail", nullable = false)
    private String detail;
}
