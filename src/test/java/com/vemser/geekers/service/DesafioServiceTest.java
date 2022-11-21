package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.DesafioRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DesafioServiceTest {

    @InjectMocks
    private DesafioService desafioService;

    @Mock
    private DesafioRepository desafioRepository;
    @Mock
    private UsuarioLoginService usuarioLoginService;
    @Mock
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(desafioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        //SETUP
        DesafioEntity desafioEntity = getDesafioEntity();
        DesafioCreateDTO desafioCreateDTO = getDesafioCreateDTO();

        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(desafioRepository.save(any())).thenReturn(desafioEntity);

        //ACT
        DesafioDTO desafioDTO = desafioService.create(desafioCreateDTO);

        //ASSERT
        assertNotNull(desafioDTO);
        assertEquals(0, desafioDTO.getResposta());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer busca = 1;
        DesafioEntity desafioEntity = getDesafioEntity();
        when(desafioRepository.findById(anyInt())).thenReturn(Optional.of(desafioEntity));
        //ACT
        DesafioDTO desafioDTO = desafioService.findById(busca);

        //ASSERT
        assertNotNull(desafioDTO);
        assertNotNull(desafioDTO.getIdDesafio());
        assertEquals(1,desafioDTO.getIdDesafio());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(desafioRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        DesafioDTO desafio = desafioService.findById(busca);

        //Assert
        assertNull(desafio);
    }

    @Test
    public void deveTestarListComSucesso(){
        List<DesafioEntity> lista = new ArrayList<>();
        lista.add(getDesafioEntity());
        when(desafioRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<DesafioDTO> list = desafioService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarFindByUsuarioComSucesso() throws RegraDeNegocioException {
        //SETUP
        DesafioDTO desafioDTO = getDesafioDTO();
        when(usuarioService.findById(anyInt())).thenReturn(getUsuarioEntity());
        when(desafioRepository.findDesafioEntityByUsuario(any())).thenReturn(getDesafioEntity());

        //ACT
        desafioService.findByUsuario(getDesafioEntity().getIdDesafio());
        //ASSERT
        assertNotNull(desafioDTO);
        assertEquals(1,desafioDTO.getIdDesafio());

    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        //SETUP
        DesafioEntity desafioEntity = getDesafioEntity();
        desafioEntity.setIdDesafio(10);
        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(desafioRepository.findDesafioEntityByUsuario(any())).thenReturn(desafioEntity);

        //ACT
        desafioService.delete();

        //ASSERT
        verify(desafioRepository, times(1)).delete(any());
    }

    @Test
    public void deveTestarEditComSucesso() throws RegraDeNegocioException {
        DesafioCreateDTO desafioCreateDTO = getDesafioCreateDTO();
        DesafioEntity desafioEntity = getDesafioEntity();
        desafioEntity.setPergunta("O thanos é imortal?");
        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(desafioRepository.findDesafioEntityByUsuario(any())).thenReturn(desafioEntity);
        DesafioEntity desafio = getDesafioEntity();
        when(desafioRepository.save(any())).thenReturn(desafio);

        //ACT
        DesafioDTO desafioDTO = desafioService.edit(desafioCreateDTO);

        //Assert
        assertNotNull(desafioDTO);
        assertNotEquals("O thanos é imortal?", desafioDTO.getPergunta());
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

    private static DesafioCreateDTO getDesafioCreateDTO(){
        DesafioCreateDTO desafioCreateDTO = new DesafioCreateDTO();
        desafioCreateDTO.setIdUsuario(1);
        desafioCreateDTO.setPergunta("Goku é mais forte que o kuririn?");
        desafioCreateDTO.setResposta(0);
        return desafioCreateDTO;
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
