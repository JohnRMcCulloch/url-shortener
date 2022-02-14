package com.mcculloch.urlshortener.util;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component("urlMurmur32Shortener")
public class UrlMurmur32Shortener implements UrlShortener {
    @Override
    public String createShortUrl(String url) {
        LocalDateTime dateTime = LocalDateTime.now();
        String shortUrl = Hashing
                .murmur3_32()
                .hashString(url.concat(dateTime.toString()), StandardCharsets.UTF_8).toString();
        return shortUrl;
    }
}
