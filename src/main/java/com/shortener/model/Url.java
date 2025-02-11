package com.shortener.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String originalUrl;

    @Getter
    @Column(nullable = false, unique = true)
    private String shortUrl;

    @Getter
    @Column(nullable = false)
    private Timestamp expiryDate;

    public Url() {
    }

    public Url(String originalUrl, String shortUrl, Timestamp expiryDate) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.expiryDate = expiryDate;
    }
}
