package com.mcculloch.urlshortener.api;

import com.mcculloch.urlshortener.model.Url;
import com.mcculloch.urlshortener.model.UrlDto;
import com.mcculloch.urlshortener.service.UrlServiceImplementation;
import org.apache.commons.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller Class to expose url-shortener api end-points to client
 *
 * @author johnmcculloch
 */
@RequestMapping("api/v1/url")
@RestController
public class UrlController {

    private final UrlServiceImplementation urlService;

    @Autowired
    public UrlController(UrlServiceImplementation urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<?> generateShortenedUrl(@RequestBody UrlDto urlDto) {
        Url urlResponse;

        try {
            urlResponse = urlService.generateShortenedUrl(urlDto);
        } catch (ValidatorException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("URL is not valid", HttpStatus.BAD_REQUEST);
        }

        if (urlResponse != null) {
            return new ResponseEntity<Url>(urlResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error creating new shortened URL", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> getShortenedUrl(@PathVariable String shortUrl) {
        Url url = urlService.getShortenedUrl(shortUrl);

        if (url != null) {
            return new ResponseEntity<Url>(url, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Url " + shortUrl + " does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<?> deleteUrl(@PathVariable String shortUrl) {
        int response = urlService.deleteUrl(shortUrl);
        if (response == 1) {
            return new ResponseEntity<String>("Short Url " + shortUrl + " deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Short Url " + shortUrl + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<Url> getAllUrls() {
        return urlService.getAllUrls();
    }
}
