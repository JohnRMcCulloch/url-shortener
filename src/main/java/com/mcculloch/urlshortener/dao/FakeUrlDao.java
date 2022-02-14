package com.mcculloch.urlshortener.dao;

import com.mcculloch.urlshortener.model.Url;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Fake database implementation of UrlDao
 * Database defined as a List, intended to be used for local development
 *
 * @author johnmcculloch
 */
@Repository("fakeDao")
public class FakeUrlDao implements UrlDao {

    private static List<Url> DB = new ArrayList<>();

    @Override
    public Url insertUrl(Url url) {
        DB.add(new Url(url.getOriginalUrl(), url.getShortenedUrl()));
        return url;
    }

    @Override
    public Url getUrl(String shortenedUrl) {
        Url urlToReturn = DB.stream()
                .filter(url -> shortenedUrl.equals(url.getShortenedUrl()))
                .findAny()
                .map(url -> new Url(
                        url.getOriginalUrl(),
                        url.getShortenedUrl()
                )).orElse(null);
        return urlToReturn;
    }

    @Override
    public int deleteUrl(String shortenedUrl) {
        Optional<Url> urlToDestroy = DB.stream()
                .filter(url -> shortenedUrl.equals(url.getShortenedUrl()))
                .findFirst();
        if(urlToDestroy.isEmpty()) {
            return 0;
        }else{
            DB.remove(urlToDestroy.get());
            return 1;
        }
    }


    @Override
    public List<Url> selectAllUrls() {
        if(DB.isEmpty()){
            return null;
        }
        return DB;
    }

}
