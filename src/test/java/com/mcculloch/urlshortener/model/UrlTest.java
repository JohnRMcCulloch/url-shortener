package com.mcculloch.urlshortener.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlTest {

    // Test Data
    private String originalUrl;
    private String shortenedUrl;
    private Url urlUnderTest;

    @BeforeEach
    void setUp() {
        originalUrl = "http://testurl.com";
        shortenedUrl = "testing";
        urlUnderTest = new Url();
    }

    @Test
    void testUrlDefaultConstructor(){
        assertNotNull(urlUnderTest);
    }

    @Test
    void testUrlConstructor(){
        Url url = new Url(originalUrl, shortenedUrl);
        assertEquals(url.getOriginalUrl(), originalUrl);
        assertEquals(url.getShortenedUrl(), shortenedUrl);
    }

    @Test
    void testSetGetOriginalUrl() {
        urlUnderTest.setOriginalUrl(originalUrl);
        assertEquals(originalUrl, urlUnderTest.getOriginalUrl());
    }

    @Test
    void testSetGetShortenedUrl() {
        urlUnderTest.setShortenedUrl(shortenedUrl);
        assertEquals(shortenedUrl, urlUnderTest.getShortenedUrl());
    }
}