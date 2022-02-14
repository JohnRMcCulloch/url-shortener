package com.mcculloch.urlshortener.dao;

import com.mcculloch.urlshortener.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Redis data structure store implementation of UrlDao
 *
 * @author johnmcculloch
 */
@Repository("redisDao")
public class RedisDao implements UrlDao {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Url insertUrl(Url url) {
        redisTemplate.opsForValue().set(url.getShortenedUrl(), url.getOriginalUrl());
        return url;
    }

    @Override
    public Url getUrl(String shortenedUrl) {
        String originalUrl = redisTemplate.opsForValue().get(shortenedUrl);
        if (originalUrl != null) {
            return new Url(originalUrl, shortenedUrl);
        }
        return null;
    }

    @Override
    public int deleteUrl(String shortenedUrl) {
        Url url = this.getUrl(shortenedUrl);
        if (url != null) {
            redisTemplate.delete(shortenedUrl);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Url> selectAllUrls() {
        Set<String> redisKeys = redisTemplate.keys("*");
        if (redisKeys != null) {
            List<Url> urlList = new ArrayList<>();
            Iterator<String> it = redisKeys.iterator();
            while (it.hasNext()) {
                String data = it.next();
                urlList.add(new Url(redisTemplate.opsForValue().get(data), data));
            }
            return urlList;
        }
        return null;
    }
}
