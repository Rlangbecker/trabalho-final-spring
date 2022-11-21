package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.LogDTO;
import com.vemser.geekers.entity.LogMatchEntity;
import com.vemser.geekers.repository.LogMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogMatchService {

    private final LogMatchRepository logMatchRepository;
    private final ObjectMapper objectMapper;
    SimpleDateFormat sdfComplete = new SimpleDateFormat("dd-MM-yyyy");

    public void createLog(LogDTO logDTO){
        var log = new LogMatchEntity();
        BeanUtils.copyProperties(logDTO, log);
        log.setData(sdfComplete.format(new Date()));
        logMatchRepository.save(log);
    }

    public List<LogDTO> listAllLogs() {
        return logMatchRepository.findAll()
                .stream()
                .map(log -> objectMapper.convertValue(log,LogDTO.class))
                .collect(Collectors.toList());

    }

    public List<LogDTO> listLogsByDataCriado(LocalDate data){
        String novaData = data.toString();
        novaData = sdfComplete.format(new Date());
        return logMatchRepository.findByDataContaining(novaData)
                .stream()
                .map(log -> objectMapper.convertValue(log, LogDTO.class))
                .collect(Collectors.toList());
    }

}
