package com.shortener.repository;

import com.shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    // Custom query
    Url findByShortUrl(String shortUrl);
    void deleteByExpiryDateBefore(Timestamp expiryDate);
}
