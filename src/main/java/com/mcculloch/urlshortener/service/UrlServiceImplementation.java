package com.mcculloch.urlshortener.service;

import com.mcculloch.urlshortener.dao.UrlDao;
import com.mcculloch.urlshortener.model.Url;
import com.mcculloch.urlshortener.model.UrlDto;
import com.mcculloch.urlshortener.util.UrlShortener;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UrlService implementation of UrlService
 * Defines the business rules and logic for the url shortener application
 *
 * @author johnmcculloch
 */
@Service
public class UrlServiceImplementation implements UrlService {

    private final UrlDao urlDao;
    private final UrlShortener urlShortener;

    @Autowired
    public UrlServiceImplementation(
            @Qualifier("redisDao") UrlDao urlDao,
            @Qualifier("urlMurmur32Shortener") UrlShortener urlShortener
    ) {
        this.urlDao = urlDao;
        this.urlShortener = urlShortener;
    }

    @Override
    public Url generateShortenedUrl(UrlDto urlDto) throws ValidatorException {
        Url newUrl = new Url();
        if (isUrlValid(urlDto.getUrl())) {
            newUrl.setOriginalUrl(urlDto.getUrl());
        } else {
            throw new ValidatorException();
        }
        newUrl.setShortenedUrl(urlShortener.createShortUrl(urlDto.getUrl()));

        return persistUrl(newUrl);
    }

    private boolean isUrlValid(String url) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"hhtp", "https"}
        );
        return urlValidator.isValid(url);
    }

    @Override
    public Url persistUrl(Url url) {
        if (url != null) {
            return urlDao.insertUrl(url);
        }
        return null;
    }

    @Override
    public Url getShortenedUrl(String url) {
        if (url != null && url.length() > 0) {
            Url urlToReturn = urlDao.getUrl(url);
            return urlToReturn;
        }
        return null;
    }

    @Override
    public int deleteUrl(String shortUrl) {
        if (shortUrl != null && shortUrl.length() > 0) {
            return urlDao.deleteUrl(shortUrl);
        }
        return 0;
    }

    public List<Url> getAllUrls() {
        return urlDao.selectAllUrls();
    }

}
