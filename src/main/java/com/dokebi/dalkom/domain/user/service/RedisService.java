package com.dokebi.dalkom.domain.user.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.user.dto.ReadUserSelfDetailResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Generated
public class RedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	// accessToken으로 refreshToken 가져오는 역할
	@Transactional(readOnly = true)
	public String readValues(String key) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		if (values.get(key) == null) {
			return "false";
		}
		return (String)values.get(key);
	}

	// key : value 형식으로 accessToken : refreshToken 저장
	public void createValues(String accessToken, String refreshToken) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(accessToken, refreshToken);
	}

	// 사용자의 개인 정보를 redis에 저장
	public void createUserDetail(String key, ReadUserSelfDetailResponse value) throws JsonProcessingException {
		String jsonValue = objectMapper.writeValueAsString(value);
		redisTemplate.opsForValue().set(key, jsonValue);
	}

	// redis에 입력받은 key값에 해당하는 사용자의 개인 정보가 있을 경우 이를 반환
	public ReadUserSelfDetailResponse getUserDetail(String key) throws JsonProcessingException {
		Object jsonValue = redisTemplate.opsForValue().get(key);
		if (jsonValue != null) {
			return objectMapper.readValue((String)jsonValue, ReadUserSelfDetailResponse.class);
		}
		return null;
	}

	// 토큰 탈취당했을 때 어떤 방법 통해 삭제해서 해커 공격 방지하는 기능 구현하면 필요할 것 같습니다.
	public void deleteValues(String key) {
		redisTemplate.delete(key);
	}

	// 추후에 사용할 일 있으면 사용
    /*
    public void expireValues(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHashOps(String key, Map<String, String> data) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.putAll(key, data);
    }

    @Transactional(readOnly = true)
    public String getHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
    }

    public void deleteHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.delete(key, hashKey);
    }

    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }
    */
}