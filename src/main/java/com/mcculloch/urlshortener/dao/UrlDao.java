package com.mcculloch.urlshortener.dao;

import com.mcculloch.urlshortener.model.Url;

import java.util.List;

public interface UrlDao {

    Url insertUrl(Url url);

    Url getUrl(String shortenedUrl);

    int deleteUrl(String shortenedUrl);

    List<Url> selectAllUrls();

}
