package com.fundoonotes.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void storeVerificationToken(String email, String token) {
        log.info("Storing verification token for email: {}", email);
        redisTemplate.opsForValue().set("verify:" + email, token, Duration.ofMinutes(15));
    }

    public void storeResetOtp(String email, String otp) {
        log.info("Storing reset OTP for email: {}", email);
        redisTemplate.opsForValue().set("reset:" + email, otp, Duration.ofMinutes(10));
    }

    public void blacklistJwt(String token, long expiryMs) {
        log.info("Blacklisting JWT");
        redisTemplate.opsForValue().set("blacklist:" + token, "blacklisted", Duration.ofMillis(expiryMs));
    }

    public String getOtp(String email) {
        log.info("Fetching OTP for email: {}", email);
        return redisTemplate.opsForValue().get("reset:" + email);
    }
}
