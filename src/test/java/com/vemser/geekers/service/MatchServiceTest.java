package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.MatchEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.MatchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioLoginService usuarioLoginService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(matchService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        //SETUP
        List<MatchEntity> lista = new ArrayList<>();
        lista.add(getMatchEntity());
        when(matchRepository.findAll()).thenReturn(lista);
        //ACT
        List<MatchDTO> list = matchService.list();

        //ASSERT
        assertNotNull(list);
        assertTrue(list.size()>0);
        assertEquals(1, list.size());


    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        //SETUP
        MatchEntity matchEntity = getMatchEntity();
        matchEntity.setIdMatch(10);
        Integer id = 10;
        when(matchRepository.findById(anyInt())).thenReturn(Optional.of(matchEntity));

        //ACT
        matchService.delete(id);
        //ASSERT
        verify(matchRepository, times(1)).save(any());


    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer busca = 1;
        MatchEntity matchEntity = getMatchEntity();
        when(matchRepository.findById(anyInt())).thenReturn(Optional.of(matchEntity));

        //ACT
        MatchDTO matchDTO = matchService.findById(busca);
        //ASSERT
        assertNotNull(matchDTO);
    }

    private static MatchEntity getMatchEntity(){
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setIdMatch(1);
        matchEntity.setIdUsuario(1);
        matchEntity.setUsuarioMain(2);
        matchEntity.setAtivo(TipoAtivo.ATIVO);
        return matchEntity;
    }

    private static MatchDTO getMatchDTO(){
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setIdMatch(1);
        matchDTO.setIdUsuario(1);
        matchDTO.setIdUsuarioMatch(2);
        matchDTO.setAtivo(TipoAtivo.ATIVO);
        return matchDTO;
    }
}
