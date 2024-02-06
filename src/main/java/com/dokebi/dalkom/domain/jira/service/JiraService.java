package com.dokebi.dalkom.domain.jira.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dokebi.dalkom.domain.jira.dto.JiraInquiryRequest;
import com.dokebi.dalkom.domain.jira.exception.MissingJiraRequestHeaderException;

import lombok.Generated;

@Service
@Generated
public class JiraService {

	private final RestTemplate restTemplate;

	@Value("${jira.url}")
	private String jiraUrl;

	@Value("${jira.project.key}")
	private String projectKey;

	@Value("${jira.user.email}")
	private String userEmail;

	@Value("${jira.api.token}")
	private String apiToken;

	public JiraService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String createIssueInquiry(JiraInquiryRequest request) {
		HttpHeaders headers = createHeaders();

		String inquiryUrl = "http://dalkom.shop/admin/inquiry/order/" + request.getInquirySeq();
		JSONObject issueDetails = new JSONObject();
		JSONObject fields = new JSONObject();
		fields.put("project", new JSONObject().put("key", projectKey));
		fields.put("issuetype", new JSONObject().put("name", "문의사항"));
		fields.put("summary", "[달콤샵 문의접수] " + request.getSummary());

		// 문의 URL
		fields.put("customfield_10039", inquiryUrl);
		// 문의 작성자
		fields.put("customfield_10040", request.getNickname());
		// 문의 카테고리
		//fields.put("customfield_10042", new JSONObject().put("name", "상품"));

		// description 필드 Atlassian Document Format으로 설정
		JSONObject description = new JSONObject();
		description.put("type", "doc");
		description.put("version", 1);

		JSONArray content = new JSONArray();

		// 문의 내용
		JSONObject paragraph1 = new JSONObject();
		paragraph1.put("type", "paragraph");
		JSONArray textContent1 = new JSONArray();
		JSONObject text1 = new JSONObject();
		text1.put("type", "text");
		text1.put("text", request.getDescription());
		textContent1.put(text1);
		paragraph1.put("content", textContent1);
		content.put(paragraph1);

		// 문의 바로가기 하이퍼링크
		JSONObject paragraph2 = new JSONObject();
		paragraph2.put("type", "paragraph");
		JSONArray textContent2 = new JSONArray();
		JSONObject text2 = new JSONObject();
		text2.put("type", "text");
		text2.put("text", "바로가기");
		JSONArray marks = new JSONArray();
		JSONObject linkMark = new JSONObject();
		linkMark.put("type", "link");
		linkMark.put("attrs", new JSONObject().put("href", inquiryUrl));
		marks.put(linkMark);
		text2.put("marks", marks);
		textContent2.put(text2);
		paragraph2.put("content", textContent2);
		content.put(paragraph2);

		description.put("content", content);

		fields.put("description", description);
		issueDetails.put("fields", fields);

		HttpEntity<String> entity = new HttpEntity<>(issueDetails.toString(), headers);

		validateHeaders(headers);

		System.out.println(issueDetails.toString());

		return restTemplate.postForObject(jiraUrl + "/rest/api/3/issue", entity, String.class);
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		if (userEmail == null || apiToken == null) {
			throw new MissingJiraRequestHeaderException();
		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(userEmail, apiToken);
		return headers;
	}

	private void validateHeaders(HttpHeaders headers) {
		if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
			throw new MissingJiraRequestHeaderException();
		}
	}
}