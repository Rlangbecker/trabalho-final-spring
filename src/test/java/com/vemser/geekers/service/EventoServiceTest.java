package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.EventoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.EventoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventoServiceTest {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(eventoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        //SETUP
        EventoEntity eventoEntity = getEventoEntity();
        EventoCreateDTO eventoCreateDTO = getEventoCreateDTO();

        when(eventoRepository.save(any())).thenReturn(eventoEntity);

        //ACT
        EventoDTO eventoDTO = eventoService.create(eventoCreateDTO,
                eventoEntity.getEvento(), eventoEntity.getDataInicial(), eventoEntity.getDataFim());

        //ASSERT
        assertNotNull(eventoDTO);
        assertEquals("Sapucai", eventoDTO.getDescricao());
    }

    @Test
    public void deveTestarFindByEventoComSucesso(){
        //SETUP
        EventoDTO eventoDTO = getEventoDTO();
        when(eventoRepository.findByEvento(any())).thenReturn(eventoDTO);

        //ACT
        EventoDTO evento = eventoService.findEvento(any());

        //ASSERT
        assertNotNull(evento);
        assertEquals(TipoEvento.CARNAVAL, evento.getEvento());

    }

    @Test
    public void deveTestarDeleteComSucesso(){
        //SETUP
        String id = "1";

        //ACT
        eventoService.delete(id);

        //ASSERT
        verify(eventoRepository, times(1)).deleteById(id);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        String busca = "1";
        EventoEntity eventoEntity = getEventoEntity();
        when(eventoRepository.findById(anyString())).thenReturn(Optional.of(eventoEntity));

        EventoDTO eventoDTO = eventoService.findById(busca);

        assertNotNull(eventoDTO);
        assertNotNull(eventoDTO.getId());
        assertEquals("Sapucai", eventoDTO.getDescricao());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String busca = "10";
        when(eventoRepository.findById(anyString())).thenReturn(Optional.empty());


        // Ação (ACT)
        EventoDTO eventoDTO = eventoService.findById(busca);

        //Assert
        assertNull(eventoDTO);
    }

    @Test
    public void deveTestarDesativarComSucesso() throws RegraDeNegocioException {
        //SETUP
        String id = "1";
        EventoEntity eventoEntity = getEventoEntity();
        when(eventoRepository.findById(anyString())).thenReturn(Optional.of(eventoEntity));
        //ACT
        EventoDTO eventoDTO = eventoService.desativar(id);

        //ASSERT
        assertNotNull(eventoDTO);
        assertEquals("INATIVO", eventoDTO.getStatus());

    }

    @Test
    public void deveTestarCountByEventosComSucesso(){
        //SETUP
        EventoEntity eventoEntity = getEventoEntity();
        when(eventoRepository.countByEvento(eventoEntity.getEvento())).thenReturn(anyInt());
        //ACT
        EventoDTOContador eventoDTOContador = eventoService.countByEventos(eventoEntity.getEvento());

        //ASSERT
        assertNotNull(eventoDTOContador);
        assertEquals(TipoEvento.CARNAVAL, eventoDTOContador.getTipoEvento());
    }

    @Test
    public void deveTestarExisteEventoComSucesso(){
        EventoEntity eventoEntity = getEventoEntity();
        EventoDTO eventoDTO = getEventoDTO();
        when(eventoRepository.findByEvento(eventoEntity.getEvento())).thenReturn(eventoDTO);

        Boolean resultado = eventoService.existeEvento(eventoEntity.getEvento());

        assertTrue(resultado);
    }


    private static EventoEntity getEventoEntity(){
        LocalDate data1 = LocalDate.of(2023,03,14);
        LocalDate data2 = LocalDate.of(2023,05,14);
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setId("1");
        eventoEntity.setEvento(TipoEvento.CARNAVAL);
        eventoEntity.setDescricao("Sapucai");
        eventoEntity.setStatus(TipoAtivo.ATIVO.name());
        eventoEntity.setDataInicial(data1);
        eventoEntity.setDataFim(data2);
        return eventoEntity;
    }

    private static EventoCreateDTO getEventoCreateDTO(){
        EventoCreateDTO eventoCreateDTO = new EventoCreateDTO();
        eventoCreateDTO.setDescricao("Sapucai");
        return eventoCreateDTO;
    }

    private static EventoDTO getEventoDTO(){
        LocalDate data1 = LocalDate.of(2023,03,14);
        LocalDate data2 = LocalDate.of(2023,05,14);
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId("1");
        eventoDTO.setEvento(TipoEvento.CARNAVAL);
        eventoDTO.setDescricao("Sapucai");
        eventoDTO.setStatus(TipoAtivo.ATIVO.name());
        eventoDTO.setDataInicial(data1);
        eventoDTO.setDataFim(data2);
        return eventoDTO;
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

}
