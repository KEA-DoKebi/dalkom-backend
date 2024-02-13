package com.dokebi.dalkom.common.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.dokebi.dalkom.domain.product.repository.elasticsearch")
@Slf4j
public class ElasticsearchConfig {
	@Value("${elastic.url}")
	String serverUrl;
	
	@Value("${elastic.key}")
	String apiKey;

	// Create the low-level client
	@Bean
	public ElasticsearchClient elasticsearchClient() {
		RestClient restClient = RestClient.builder(HttpHost.create(serverUrl))
			.setDefaultHeaders(new Header[] {
				new BasicHeader("Authorization", "ApiKey " + apiKey)
			})
			.build();

		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		return new ElasticsearchClient(transport);
	}

}