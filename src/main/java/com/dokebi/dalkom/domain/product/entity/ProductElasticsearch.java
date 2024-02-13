package com.dokebi.dalkom.domain.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@Document(indexName = "product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductElasticsearch {
	@Id
	private Long productSeq;
	private String name;
	private Long price;
	private Long categorySeq;
	private String company;
	private String state;
	private String info;
}
