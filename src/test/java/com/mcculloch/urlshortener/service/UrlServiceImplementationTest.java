package com.mcculloch.urlshortener.service;

import com.mcculloch.urlshortener.dao.FakeUrlDao;
import com.mcculloch.urlshortener.model.Url;
import com.mcculloch.urlshortener.model.UrlDto;
import com.mcculloch.urlshortener.util.UrlMurmur32Shortener;
import org.apache.commons.validator.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplementationTest {

    //Test Data
    @Mock
    private FakeUrlDao fakeUrlDaoMock;
    @Mock
    private UrlMurmur32Shortener urlMurmur32ShortenerMock;
    @Mock
    private UrlDto urlDtoMock;
    @Mock
    private Url urlMock;

    private String clientUrlValid;
    private String clientUrlInvalidLength;
    private String clientUrlNull;
    private String clientShortUrlValid;
    private String clientShortUrlInvalidLength;
    private String clientShortNull;

    private UrlServiceImplementation serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new UrlServiceImplementation(fakeUrlDaoMock, urlMurmur32ShortenerMock);
        clientUrlValid = "www.google.com";
        clientUrlInvalidLength = "";
        clientShortUrlValid = "test";
        clientShortUrlInvalidLength = "";
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(serviceUnderTest);
    }

    @Test
    void generateShortenedUrl() throws ValidatorException {
        Mockito.when(urlDtoMock.getUrl()).thenReturn("https://www.google.com");
        Mockito.when(urlMock.getShortenedUrl()).thenReturn("shortUrl");
        Mockito.when(urlMock.getOriginalUrl()).thenReturn("longUrl");
        Mockito.when(fakeUrlDaoMock.insertUrl(any())).thenReturn(urlMock);

        Url checkUrl = serviceUnderTest.generateShortenedUrl(urlDtoMock);

        verify(urlMurmur32ShortenerMock).createShortUrl(urlDtoMock.getUrl());
        assertNotNull(checkUrl);
        assertEquals(checkUrl.getOriginalUrl(), "longUrl");
        assertEquals(checkUrl.getShortenedUrl(), "shortUrl");
    }

    @Test
    void generateShortenedUrlValidatorException() {
        Mockito.when(urlDtoMock.getUrl()).thenReturn("google.fake");
        assertThrows(ValidatorException.class, () -> {
            serviceUnderTest.generateShortenedUrl(urlDtoMock);
        });
    }

    @Test
    void persistUrl() {
        Mockito.when(urlMock.getShortenedUrl()).thenReturn("returned");
        Mockito.when(fakeUrlDaoMock.insertUrl(any())).thenReturn(urlMock);

        Url checkUrl = serviceUnderTest.persistUrl(urlMock);

        verify(fakeUrlDaoMock).insertUrl(urlMock);
        assertEquals(checkUrl.getShortenedUrl(), "returned");
    }

    @Test
    void persistUrlInvalidNull() {
        Url checkUrl = serviceUnderTest.persistUrl(null);
        verify(fakeUrlDaoMock, never()).insertUrl(null);
        assertNull(checkUrl);
    }


    @Test
    void getShortenedUrl() {
        Mockito.when(urlMock.getShortenedUrl()).thenReturn("returnedShortUrl");
        Mockito.when(fakeUrlDaoMock.getUrl(anyString())).thenReturn(urlMock);

        Url checkUrl = serviceUnderTest.getShortenedUrl(clientUrlValid);

        verify(fakeUrlDaoMock).getUrl(clientUrlValid);
        assertEquals(checkUrl.getShortenedUrl(), "returnedShortUrl");
    }

    @Test
    void getShortenedUrlInvalidNull() {
        Url checkUrl = serviceUnderTest.getShortenedUrl(clientUrlNull);

        verify(fakeUrlDaoMock, never()).getUrl(any());
        assertNull(checkUrl);
    }

    @Test
    void getShortenedUrlInvalidLength() {
        Url checkUrl = serviceUnderTest.getShortenedUrl(clientUrlInvalidLength);

        verify(fakeUrlDaoMock, never()).getUrl(any());
        assertNull(checkUrl);
    }

    @Test
    void deleteUrl() {
        Mockito.when(fakeUrlDaoMock.deleteUrl(clientShortUrlValid)).thenReturn(1);

        int check = serviceUnderTest.deleteUrl(clientShortUrlValid);

        verify(fakeUrlDaoMock).deleteUrl(clientShortUrlValid);
        assertEquals(check, 1);
    }

    @Test
    void deleteUrlInvalidClientUrlNull() {
        int check = serviceUnderTest.deleteUrl(clientShortNull);

        verify(fakeUrlDaoMock, never()).deleteUrl(any());
        assertEquals(check, 0);
    }

    @Test
    void deleteUrlInvalidClientUrlLengthInvalid() {
        int check = serviceUnderTest.deleteUrl(clientShortUrlInvalidLength);

        verify(fakeUrlDaoMock, never()).deleteUrl(any());
        assertEquals(check, 0);
    }

}