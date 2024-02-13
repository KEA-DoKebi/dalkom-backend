package com.dokebi.dalkom.domain.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.product.entity.ProductElasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductElasticService {

	private final ElasticsearchClient elasticsearchClient;

	@Autowired
	public ProductElasticService(ElasticsearchClient elasticsearchClient) {
		this.elasticsearchClient = elasticsearchClient;
	}

	public List<Long> findProductBySearchValue(String searchValue) {
		List<Long> productSeqList = new ArrayList<>();
		try {
			SearchResponse<ProductElasticsearch> response = elasticsearchClient.search(s -> s
				.index("product-dev") // 검색할 인덱스 지정
				.query(q -> q
					.bool(b -> b
						.should(sh -> sh
							.match(m -> m
								.field("name") // 첫 번째 검색 조건: name 필드
								.query(searchValue)
							))
						.should(sh -> sh
							.match(m -> m
								.field("info") // 두 번째 검색 조건: info 필드
								.query(searchValue)
							))
					)
				), ProductElasticsearch.class
			);

			System.out.println(response);
			if (!response.hits().hits().isEmpty()) {
				for (Hit<ProductElasticsearch> hit : response.hits().hits()) {
					ProductElasticsearch product = hit.source();
					productSeqList.add(product.getProductSeq());

					log.info("Found product: " + product.getName());
					log.info("Found product ID: " + product.getProductSeq());
				}
			} else {
				log.info("Product not found");
			}
		} catch (Exception e) {
			log.error("Error searching product by name in Elasticsearch", e);
		}
		return productSeqList;
	}
}