package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioLoginService usuarioLoginService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    // SETUP - Criar variáveis.


    // ACT - Ação, ou seja, testar o método.


    // ASSERT - Verificação do método.

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        List<UsuarioEntity> lista = new ArrayList<>();
        lista.add(getUsuarioEntity());
        when(usuarioRepository.findAll()).thenReturn(lista);

        // ACT - Ação, ou seja, testar o método.
        List<UsuarioDTO> list = usuarioService.list();

        // ASSERT - Verificação do método.
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
    } // List

    @Test
    public void deveTestarListByAtivoComSucesso() {

        // SETUP - Criar variáveis.
        List<UsuarioEntity> lista = new ArrayList<>();
        lista.add(getUsuarioEntity());
        when(usuarioRepository.findByAtivo(TipoAtivo.ATIVO)).thenReturn(lista);

        // ACT - Ação, ou seja, testar o método.
        List<UsuarioDTO> listAtivo = usuarioService.listByAtivo();

        // ASSERT - Verificação do método.
        assertNotNull(listAtivo);
        assertTrue(listAtivo.size() > 0);
        assertEquals(1, lista.size());
    } // ListByAtivo

    @Test
    public void deveTestarListByInativoComSucesso() {
        // SETUP - Criar variáveis.
        List<UsuarioEntity> lista = new ArrayList<>();
        lista.add(getUsuarioEntityInativo());
        when(usuarioRepository.findByAtivo(TipoAtivo.INATIVO)).thenReturn(lista);

        // ACT - Ação, ou seja, testar o método.
        List<UsuarioDTO> listInativo = usuarioService.listByInativo();


        // ASSERT - Verificação do método.
        assertNotNull(listInativo);
        assertTrue(listInativo.size() > 0);
        assertEquals(1, lista.size());
    } // ListByInativo

    @Test
    public void deveTestarRemoverUsuarioComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        // ACT - Ação, ou seja, testar o método.
        usuarioService.removerUsuario(usuarioEntity.getIdUsuario());

        // ASSERT - Verificação do método.
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        Integer busca = 10;
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // ACT - Ação, ou seja, testar o método.
        UsuarioEntity usuarioEntity1 = usuarioService.findById(busca);

        // ASSERT - Verificação do método.
        assertNotNull(usuarioEntity1);
        assertNotNull(usuarioEntity1.getIdUsuario());
        assertEquals(10, usuarioEntity1.getIdUsuario());
    }


    @Test(expected = RegraDeNegocioException.class) // fazer
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        Integer busca = 10;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // ACT - Ação, ou seja, testar o método.
        UsuarioEntity usuarioEntity = usuarioService.findById(busca);

        // ASSERT - Verificação do método.
        assertNull(usuarioEntity);
    }

    @Test
    public void deveTestarFindByNameComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        String busca = "Gustavo Linck";
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        List<UsuarioEntity> lista = new ArrayList<>();
        lista.add(usuarioEntity);


        when(usuarioRepository.findUsuarioEntityByNome(any())).thenReturn(lista);

        // ACT - Ação, ou seja, testar o método.
        List<UsuarioDTO> list = usuarioService.findByName(busca);

        // ASSERT - Verificação do método.
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, list.size());
    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(10);
        usuarioEntity.setAtivo(TipoAtivo.ATIVO);
        usuarioEntity.setNome("Gustavo Linck");
        usuarioEntity.setEmail("linck@gmail.com");
        usuarioEntity.setLogin("linck");
        usuarioEntity.setSenha("123");
        usuarioEntity.setSexo("M");
        return usuarioEntity;
    }

    private static UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(10);
        usuarioDTO.setAtivo(TipoAtivo.ATIVO);
        usuarioDTO.setNome("Gustavo Linck");
        usuarioDTO.setEmail("linck@gmail.com");
        usuarioDTO.setLogin("linck");
        usuarioDTO.setSenha("123");
        usuarioDTO.setSexo("M");
        return usuarioDTO;
    }


    private static UsuarioEntity getUsuarioEntityInativo() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(10);
        usuarioEntity.setAtivo(TipoAtivo.INATIVO);
        usuarioEntity.setNome("Gustavo Linck");
        usuarioEntity.setEmail("linck@gmail.com");
        usuarioEntity.setLogin("linck");
        usuarioEntity.setSenha("123");
        usuarioEntity.setSexo("M");
        return usuarioEntity;
    }

    private static LoginWithIdDTO getLogin() {
        LoginWithIdDTO login = new LoginWithIdDTO();
        login.setIdUsuario(1);
        login.setNome("Gustavo Linck");
        login.setEmail("linck@gmail.com");
        login.setLogin("linck");
        login.setSenha("123");
        return login;
    }
}
