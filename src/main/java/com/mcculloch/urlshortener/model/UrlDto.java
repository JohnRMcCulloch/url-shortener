package com.mcculloch.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UrlDto model represents data transaction object to encapsulate users url
 *
 * @author johnmcculloch
 */
public class UrlDto {

    private String url;

    public UrlDto(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
