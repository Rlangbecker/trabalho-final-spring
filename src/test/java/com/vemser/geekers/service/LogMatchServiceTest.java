package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.LogDTO;
import com.vemser.geekers.entity.LogMatchEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.repository.LogMatchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogMatchServiceTest {

    @InjectMocks
    private LogMatchService logMatchService;

    @Mock
    private LogMatchRepository logMatchRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(logMatchService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateLogComSucesso(){
        //SETUP
        LogMatchEntity logMatchEntity = getLogEntity();
        when(logMatchRepository.save(any())).thenReturn(logMatchEntity);
        LogDTO logDTO = getLogDTO();
        //ACT
        logMatchService.createLog(logDTO);
        //ASSERT
        assertNotNull(logDTO);

    }

    @Test
    public void deveTestarListAllLogsComSucesso(){
        List<LogMatchEntity> lista = new ArrayList<>();
        lista.add(getLogEntity());
        when(logMatchRepository.findAll()).thenReturn(lista);

        List<LogDTO> list = logMatchService.listAllLogs();

        assertNotNull(list);
        assertTrue(list.size()>0);
        assertEquals(1, list.size());
    }

    @Test
    public void deveTestarListLogsByDataCriadoComSucesso(){
        LogMatchEntity logMatchEntity = getLogEntity();
        LocalDate data = LocalDate.of(2022,03,01);
        List<LogMatchEntity> lista = new ArrayList<>();
        lista.add(logMatchEntity);
        when(logMatchRepository.findByData(any())).thenReturn(lista);

        List<LogDTO> list = logMatchService.listLogsByDataCriado(data);

        assertNotNull(list);
        assertTrue(list.size()>0);

    }

    private static LogMatchEntity getLogEntity(){
        LogMatchEntity logMatchEntity = new LogMatchEntity();
        logMatchEntity.setId("1");
        logMatchEntity.setData("2022-03-01");
        logMatchEntity.setIdUsuario(1);
        logMatchEntity.setUsuarioMain(2);
        logMatchEntity.setAtivo(TipoAtivo.ATIVO);
        return logMatchEntity;

    }

    private static LogDTO getLogDTO(){
        LogDTO logDTO = new LogDTO();
        logDTO.setId("1");
        logDTO.setData("2022-03-01");
        logDTO.setIdUsuario(1);
        logDTO.setUsuarioMain(2);
        logDTO.setAtivo(TipoAtivo.ATIVO);
        return logDTO;
    }
}
