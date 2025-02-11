package com.shortener.service;

import com.shortener.model.Url;
import com.shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService {


    private final UrlRepository urlRepository;


    private final RedisTemplate<String, Url> redisTemplate;

    public UrlService(UrlRepository urlRepository, RedisTemplate<String, Url> redisTemplate) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    private static final String URL_PREFIX = "url:";

    public String generateShortUrl(String originalUrl) {
        // Check Redis cache first
        Url existingUrl = redisTemplate.opsForValue().get(URL_PREFIX + originalUrl);
        if (existingUrl != null && existingUrl.getOriginalUrl().equals(originalUrl)) {
            return existingUrl.getShortUrl(); // Return from cache if exists
        }

        String shortUrl = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        Timestamp expiryDate = Timestamp.valueOf(LocalDateTime.now().plusDays(7));

        // Check if short URL already exists, regenerate if needed
        while (urlRepository.findByShortUrl(shortUrl) != null) {
            shortUrl = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        }

        // Save the new URL mapping to the database
        Url url = new Url(originalUrl, shortUrl, expiryDate);
        urlRepository.save(url);

        // Cache the short URL in Redis
        redisTemplate.opsForValue().set(URL_PREFIX + originalUrl, url);
        redisTemplate.opsForValue().set(URL_PREFIX + shortUrl, url);

        return shortUrl;
    }

    public Url getUrl(String shortUrl) {
        Url existingUrl = redisTemplate.opsForValue().get(URL_PREFIX + shortUrl);
        if (existingUrl != null && existingUrl.getShortUrl().equals(shortUrl)) {
            return existingUrl; // Return from cache if exists
        }
        return urlRepository.findByShortUrl(shortUrl);
    }

    // Scheduled task to clean expired URLs every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        urlRepository.deleteByExpiryDateBefore(Timestamp.valueOf(now));
    }
}
