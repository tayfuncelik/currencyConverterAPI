package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface FLogger {
    default Logger getLogger(){
        return LoggerFactory.getLogger(getClass().getName());
    }
}