package com.mcculloch.urlshortener.util;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Url Murmur 32 Shortener implementation of Url Shortener
 *
 * @author johnmcculloch
 */
@Component("urlMurmur32Shortener")
public class UrlMurmur32Shortener implements UrlShortener {

    /**
     * Returns a short url identifier
     *
     * Method concatenates url with LocalDateTime to ensure unique identifier
     * Concatenated url then hashed using Murmur3_32 algorithm and UTF_8 to provide short identifier
     *
     * @param url a validated url
     * @return A short url identifier
     */
    @Override
    public String createShortUrl(String url) {
        LocalDateTime dateTime = LocalDateTime.now();
        String shortUrl = Hashing
                .murmur3_32()
                .hashString(url.concat(dateTime.toString()), StandardCharsets.UTF_8).toString();
        return shortUrl;
    }
}
