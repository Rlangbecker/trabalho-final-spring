package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.MatchEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.MatchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private EventoService eventoService;

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private DesafioService desafioService;
    @Mock
    private LogMatchService logMatchService;
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

    @Test
    public void deveTestarListByUser() throws RegraDeNegocioException {
        List<MatchDTO> lista = new ArrayList<>();
        Integer id = 1;
        lista.add(getMatchDTO());
        List<MatchEntity> listaDeEntity = new ArrayList<>();
        listaDeEntity.add(getMatchEntity());
        when(usuarioService.findById(anyInt())).thenReturn(getUsuarioEntity());
        when(matchRepository.findMatchEntitiesByUsuario(any())).thenReturn(listaDeEntity);

        List<MatchDTO> listaDTO = matchService.listByUser(id);

        assertNotNull(listaDTO);


    }

    @Test
    public void deveTestarResolverDesafioComSucesso() throws RegraDeNegocioException {
        MatchEntity matchEntity = getMatchEntity();
        MatchCreateDTO matchCreateDTO = getMatchCreateDTO();
        DesafioDTO desafio = getDesafioDTO();

        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(desafioService.findByUsuario(any())).thenReturn(desafio);
        when(eventoService.existeEvento(TipoEvento.CARNAVAL)).thenReturn(Boolean.TRUE);
        when(matchRepository.save(any())).thenReturn(matchEntity);
        logMatchService.createLog(getLogDTO());
        MatchDTO matchDTO = matchService.resolverDesafio(matchCreateDTO, 0,  TipoEvento.CARNAVAL);

        assertNotNull(matchDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarResolverDesafioComErro() throws RegraDeNegocioException {
        MatchEntity matchEntity = getMatchEntity();
        MatchCreateDTO matchCreateDTO = getMatchCreateDTO();
        DesafioDTO desafio = getDesafioDTO();

        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(desafioService.findByUsuario(any())).thenReturn(desafio);
        when(eventoService.existeEvento(TipoEvento.CARNAVAL)).thenReturn(Boolean.TRUE);
        logMatchService.createLog(getLogDTO());
        MatchDTO matchDTO = matchService.resolverDesafio(matchCreateDTO, 1,  TipoEvento.CARNAVAL);

        assertNull(matchDTO);
    }

    @Test
    public void deveTestarListPaginadoComSucesso(){
        // SETUP
        Integer pagina = 10;
        Integer quantidade = 5;

        //pessoaRepository.findAll(pageable);
        MatchEntity matchEntity = getMatchEntity();
        Page<MatchEntity> paginaMock = new PageImpl<>(List.of(matchEntity));
        when(matchRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<MatchDTO> paginaSolicitada = matchService.listMatchPaginada(pagina, quantidade);

        // ASSERT
        assertNotNull(paginaSolicitada);
        assertNotNull(paginaSolicitada.getPagina());
        assertEquals(1, paginaSolicitada.getTotalElementos());
    }

    private static MatchEntity getMatchEntity(){
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setIdMatch(1);
        matchEntity.setIdUsuario(1);
        matchEntity.setUsuarioMain(2);
        matchEntity.setAtivo(TipoAtivo.ATIVO);
        return matchEntity;
    }

    private static MatchCreateDTO getMatchCreateDTO(){
        MatchCreateDTO matchCreateDTO = new MatchCreateDTO();
        matchCreateDTO.setIdUsuario(1);
        matchCreateDTO.setIdUsuarioMatch(2);
        matchCreateDTO.setAtivo(TipoAtivo.ATIVO);
        return matchCreateDTO;
    }
    private static MatchDTO getMatchDTO(){
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setIdMatch(1);
        matchDTO.setIdUsuario(1);
        matchDTO.setIdUsuarioMatch(2);
        matchDTO.setAtivo(TipoAtivo.ATIVO);
        return matchDTO;
    }

    private static UsuarioEntity getUsuarioEntity(){
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setAtivo(TipoAtivo.ATIVO);
        usuarioEntity.setNome("Kaio");
        usuarioEntity.setEmail("kaio@gmail.com");
        usuarioEntity.setLogin("kaio");
        usuarioEntity.setSenha("123");
        usuarioEntity.setSexo("M");
        return usuarioEntity;
    }

    private static LoginWithIdDTO getLogin(){
        LoginWithIdDTO login = new LoginWithIdDTO();
        login.setIdUsuario(1);
        login.setNome("Kaio");
        login.setEmail("kaio@gmail.com");
        login.setLogin("kaio");
        login.setSenha("123");
        return login;
    }
    private static DesafioEntity getDesafioEntity(){
        DesafioEntity desafioEntity = new DesafioEntity();
        desafioEntity.setIdDesafio(1);
        desafioEntity.setPergunta("Goku é mais forte que o kuririn?");
        desafioEntity.setResposta(0);
        desafioEntity.setUsuario(getUsuarioEntity());
        return desafioEntity;
    }
    private static DesafioDTO getDesafioDTO(){
        DesafioDTO desafioDTO = new DesafioDTO();
        desafioDTO.setIdDesafio(1);
        desafioDTO.setIdUsuario(1);
        desafioDTO.setPergunta("Goku é mais forte que o kuririn?");
        desafioDTO.setResposta(0);
        return desafioDTO;
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
