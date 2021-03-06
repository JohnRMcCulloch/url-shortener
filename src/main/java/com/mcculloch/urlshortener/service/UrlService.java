package com.mcculloch.urlshortener.service;

import com.mcculloch.urlshortener.model.Url;
import com.mcculloch.urlshortener.model.UrlDto;
import org.apache.commons.validator.ValidatorException;

/**
 * UrlService interface containing abstract methods to build logical service layer
 *
 * @author johnmcculloch
 */
public interface UrlService {

    Url generateShortenedUrl(UrlDto urlDto) throws ValidatorException;
    Url persistUrl(Url url);
    Url getShortenedUrl(String shortUrl);
    int deleteUrl(String shortUrl);

}
