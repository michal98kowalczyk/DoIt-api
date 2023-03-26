package com.example.doitapi.service;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeService {

    public static Date getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }
}
