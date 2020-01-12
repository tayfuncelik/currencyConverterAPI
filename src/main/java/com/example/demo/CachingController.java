package com.example.demo;

import com.example.demo.service.CachingService;
import com.example.demo.utils.FLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CachingController implements FLogger {

    @Autowired
    private CachingService cachingService;

    @GetMapping("clearAllCaches")
    public void clearAllCaches() {
        getLogger().info("All caches will be deleted");
        cachingService.evictAllCaches();
    }
}
