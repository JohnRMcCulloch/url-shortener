package com.mcculloch.urlshortener.dao;

import com.mcculloch.urlshortener.model.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeUrlDaoTest {

    //Test Data
    private String shortenedUrl;
    private String originalUrl;
    private Url url;
    private FakeUrlDao fakeUrlDaoUnderTest;

    @BeforeEach
    void setUp() {
        originalUrl = "http://testurl.com";
        shortenedUrl = "testing";
        url = new Url(originalUrl, shortenedUrl);

        fakeUrlDaoUnderTest = new FakeUrlDao();
    }

    @Test
    void testUrlDefaultConstructor(){
        assertNotNull(fakeUrlDaoUnderTest);
    }

    @Test
    void insertGetUrl() {
        fakeUrlDaoUnderTest.insertUrl(url);
        Url checkUrl = fakeUrlDaoUnderTest.getUrl(shortenedUrl);
        String originalUrlCheck = checkUrl.getOriginalUrl();
        String shortenedUrlCheck = checkUrl.getShortenedUrl();
        assertEquals(originalUrl, originalUrlCheck);
        assertEquals(shortenedUrl, shortenedUrlCheck);
    }

    @Test
    void getUrlReturnsNull() {
        Url checkUrl = fakeUrlDaoUnderTest.getUrl(shortenedUrl);
        assertNull(checkUrl);
    }

    @Test
    void deleteUrl() {
        fakeUrlDaoUnderTest.insertUrl(url);
        int checkUrlDeletedResponse = fakeUrlDaoUnderTest.deleteUrl(shortenedUrl);
        Url checkUrlDeleted = fakeUrlDaoUnderTest.getUrl(shortenedUrl);
        assertEquals(1, checkUrlDeletedResponse);
        assertNull(checkUrlDeleted);
    }

    @Test
    void deleteUrlNotFound() {
        int checkUrlDeletedResponse = fakeUrlDaoUnderTest.deleteUrl(shortenedUrl);
        assertEquals(0, checkUrlDeletedResponse);
    }

}