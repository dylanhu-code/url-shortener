package com.shortener.controller;

import com.shortener.model.Url;
import com.shortener.service.RateLimitService;
import com.shortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class UrlController {


    private final UrlService urlService;
    private final RateLimitService rateLimiterService;
    Dotenv dotenv = Dotenv.load();

    public UrlController(UrlService urlService, RateLimitService rateLimiterService) {
        this.urlService = urlService;
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index"; // Render the index.html page
    }

    // Handle the form submission
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url, Model model, HttpServletRequest httpRequest) {
        String clientIp = httpRequest.getRemoteAddr(); // Get client IP

        // Check rate limit
        if (!rateLimiterService.isAllowed(clientIp)) {
            model.addAttribute("error", "Rate limit exceeded. Try again later.");
            return "index"; // Redirect back to home page with error message
        }

        String shortUrl = urlService.generateShortUrl(url);
        String fullShortUrl = dotenv.get("BASE_URL") + shortUrl;

        // Add the original URL and short URL to the model attributes for the redirect
        model.addAttribute("originalUrl", url);
        model.addAttribute("shortUrl", fullShortUrl);

        // Redirect to the index page (home page)
        return "index";
    }

    // Endpoint to redirect to the original URL from a short URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String shortUrl) {
        Url url = urlService.getUrl(shortUrl);
        if (url == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Shortened URL not found");
        }

        if (url.getExpiryDate() != null && url.getExpiryDate().toLocalDateTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE)
                    .body("This URL has expired");
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
}

