package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.LogDTO;
import com.vemser.geekers.entity.LogMatchEntity;
import com.vemser.geekers.repository.LogMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogMatchService {

    private final LogMatchRepository logMatchRepository;
    private final ObjectMapper objectMapper;
    SimpleDateFormat sdfComplete = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public void createLog(LogDTO logDTO){
        var log = new LogMatchEntity();
        BeanUtils.copyProperties(logDTO, log);
        log.setData(sdfComplete.format(new Date()));
        logMatchRepository.save(log);
    }

}
