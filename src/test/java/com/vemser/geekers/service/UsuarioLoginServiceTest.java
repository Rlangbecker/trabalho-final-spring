package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.CargoRepository;
import com.vemser.geekers.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioLoginServiceTest {

    @InjectMocks
    private UsuarioLoginService usuarioLoginService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CargoRepository cargoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis
        Integer busca = 1;
        UsuarioEntity usuario = getUsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));

        // ACT - Ação, ou seja, testar o método.
        UsuarioEntity usuarioEntity = usuarioLoginService.findById(busca);

        // ASSERT - Verificação do método.
        assertNotNull(usuarioEntity);
        assertEquals(1, usuarioEntity.getIdUsuario());

    } // FindById

//    @Test
//    public void deveTestarFindByEmailComSucesso() throws RegraDeNegocioException {
//
//        // SETUP - Criar variáveis
//        String busca = "linck@gmail.com";
//        UsuarioEntity usuario = getUsuarioEntity();
//
//        when(usuarioRepository.findByEmail(busca)).thenReturn(usuario);
//        when(usuarioRepository.save(any())).thenReturn(usuario);
//
//
//        // ACT - Ação, ou seja, testar o método.
//        String string = usuarioLoginService.findUserByEmail(any());
//
//        // ASSERT - Verificação do método.
//        assertNotNull(string);
//
//        verify(usuarioRepository, times(1)).findByEmail(any());
//
//    } // FindByEmail (n sei se ta certo)

    @Test
    public void deveTestarFindByLoginComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis
        String busca = "linck";
        UsuarioEntity usuario = getUsuarioEntity();

        when(usuarioRepository.findByLogin(any())).thenReturn(Optional.of(usuario));

        // ACT - Ação, ou seja, testar o método.
        Optional<UsuarioEntity> usuarioEntity = usuarioLoginService.findByLogin(usuario.getLogin());

        // ASSERT - Verificação do método.
        assertNotNull(usuarioEntity);
        assertEquals(busca, usuarioEntity.get().getLogin());

    }

//    @Test
//    public void deveTestarEditarUsuarioComSucesso() throws RegraDeNegocioException {
//
//        // SETUP - Criar variáveis
//        UsuarioDTO usuarioDTO = getUsuarioDTO();
//        UsuarioEntity usuario = getUsuarioEntity();
//
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList()));
////
//        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
//        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
//        when(usuarioRepository.save(any())).thenReturn(usuario);
//
//        // ACT - Ação, ou seja, testar o método.
//        UsuarioDTO usuarioDTOEdit = usuarioLoginService.editarUsuario(usuarioDTO);
//
//        // ASSERT - Verificação do método.
//        assertNotNull(usuarioDTOEdit);
//        verify(usuarioLoginService, times(1)).editarUsuario(usuarioDTO);
//
//    } deu erro

//    @Test
//    public void deveTestarAtualizarCargoComSucesso() throws RegraDeNegocioException {
//
//        // SETUP - Criar variáveis
//        UsuarioDTO usuarioDTO = getUsuarioDTO();
//        UsuarioEntity usuario = getUsuarioEntity();
//
//        Integer idUsuario = usuarioDTO.getIdUsuario();
//
//        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
//        when(cargoRepository.findById(anyInt())).thenReturn(usuario);
//
//        // ACT - Ação, ou seja, testar o método.
//        UsuarioDTO usuarioDTOEdit = usuarioLoginService.atualizarCargo(usuarioDTO);
//
//        // ASSERT - Verificação do método.
//        assertNotNull(usuarioDTOEdit);
//        verify(usuarioLoginService, times(1)).atualizarCargo(usuarioDTO);
//
//    } não consegui fazer


    @Test
    public void deveTestarTrocarSenhaComSucesso() throws RegraDeNegocioException {

//        // SETUP - Criar variáveis
//        LoginWithIdDTO loginWithIdDTO = getLogin();
//        UsuarioEntity usuario = getUsuarioEntity();
//        String senhaNova = "456";
//
//        when(usuarioLoginService.getLoggedUser()).thenReturn(loginWithIdDTO);
//        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
//        when(usuarioRepository.save(any())).thenReturn(usuario);
//
//        // ACT - Ação, ou seja, testar o método.
//        String usuarioEntity = usuarioLoginService.trocarSenha(senhaNova);
//
//        // ASSERT - Verificação do método.
//        assertNotNull(usuarioEntity);
//        assertEquals("456", usuarioEntity);


    } // TrocarSenha

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

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setAtivo(TipoAtivo.ATIVO);
        usuarioEntity.setNome("Gustavo Linck");
        usuarioEntity.setEmail("linck@gmail.com");
        usuarioEntity.setLogin("linck");
        usuarioEntity.setSenha("123");
        usuarioEntity.setSexo("M");
        return usuarioEntity;
    }

    private static LoginWithIdDTO getLogin(){
        LoginWithIdDTO login = new LoginWithIdDTO();
        login.setIdUsuario(1);
        login.setNome("Gustavo Linck");
        login.setEmail("linck@gmail.com");
        login.setLogin("linck");
        login.setSenha("123");
        return login;
    }

}
