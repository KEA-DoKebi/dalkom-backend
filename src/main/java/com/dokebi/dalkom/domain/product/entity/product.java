package com.dokebi.dalkom.domain.product.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.cart.entity.ordrCart;
import com.dokebi.dalkom.domain.category.entity.category;
import com.dokebi.dalkom.domain.order.entity.ordrDetail;
import com.dokebi.dalkom.domain.stock.entity.prdtStock;
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
public class product extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSeq;

    @ManyToOne
    @JoinColumn(name = "categorySeq")
    private category category;

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
    private List<prdtStock> prdtStock = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<prdtImage> prdtImage = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ordrDetail> ordrDetail = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ordrCart> ordrCart = new ArrayList<>();

}
