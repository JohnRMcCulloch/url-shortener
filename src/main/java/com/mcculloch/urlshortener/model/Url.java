package com.mcculloch.urlshortener.model;

/**
 * Url Model representing data associated with url shortener
 *
 * @author johnmcculloch
 */
public class Url {

    private String originalUrl;
    private String shortenedUrl;

    public Url() {
    }

    public Url(String originalUrl, String shortenedUrl) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }
}
