
package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.entity.ComentarioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.ComentarioRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTest {

    @InjectMocks // clase principal de testes.
    private ComentarioService comentarioService;

    @Mock
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioLoginService usuarioLoginService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(comentarioService, "objectMapper", objectMapper);
    }

    // SETUP - Criar variáveis.


    // ACT - Ação, ou seja, testar o método.


    // ASSERT - Verificação do método.


    @Test
    public void deveTestarSaveComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        ComentarioEntity comentarioEntity = getComentarioEntity();
        comentarioEntity.setIdUsuario(10);
        comentarioEntity.setIdComentario(1);
        when(usuarioService.findById(any())).thenReturn(getUsuarioEntity());
        when(comentarioRepository.save(any())).thenReturn(comentarioEntity);

        // ACT - Ação, ou seja, testar o método.

        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioRepository.save(comentarioEntity), ComentarioDTO.class);

        // ASSERT - Verificação do método.

        assertNotNull(comentarioDTO);
        assertNotNull(comentarioDTO.getIdComentario());
        assertEquals("eu também gosto de dragon ball!", comentarioDTO.getComentario());

    } // "create"

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
    Integer id = 10;

        ComentarioEntity comentarioEntity = getComentarioEntity();

        comentarioEntity.setIdComentario(10);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentarioEntity));
        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(comentarioRepository.findById(any())).thenReturn(Optional.of(comentarioEntity));

        // ACT - Ação, ou seja, testar o método.
        comentarioRepository.delete(comentarioEntity);

        // ASSERT - Verificação do método.
        verify(comentarioRepository, times(1)).delete(any());

    } // "delete"

    @Test
    public void deveTestarEditarComentarioComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        ComentarioDTO comentarioCreate = getComentarioDTO();
    ComentarioEntity comentarioEntity = getComentarioEntity();
    comentarioEntity.setComentario("Gostei do seu perfil");
    comentarioEntity.setIdComentario(10);
        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(comentarioRepository.findById(any())).thenReturn(Optional.of(comentarioEntity));
        when(comentarioRepository.save(any())).thenReturn(comentarioEntity);

        // ACT - Ação, ou seja, testar o método.
        ComentarioDTO comentarioDTO = comentarioService.editarComentario(comentarioEntity.getIdComentario(), comentarioCreate);

        // ASSERT - Verificação do método.
        assertNotNull(comentarioDTO);
        assertNotEquals("Gostei do seu perfil", comentarioDTO.getComentario());
    } // "Update"

    @Test
    public void deveTestarListarComentarioPorUsuarioComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        ComentarioEntity comentarioEntity = getComentarioEntity();
        List<ComentarioEntity> listaComentarioByUsuario = new ArrayList<>();
        listaComentarioByUsuario.add(comentarioEntity);
        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);
        when(comentarioRepository.findComentarioEntityByUsuario(any())).thenReturn(listaComentarioByUsuario);

        // ACT - Ação, ou seja, testar o método.
        List<ComentarioDTO> listaComentarios = comentarioService.listarComentarioPorUsuario(getComentarioEntity().getIdUsuario());

        // ASSERT - Verificação do método.UsuarioEntity usuarioEntity = getUsuarioEntity();
        assertNotNull(listaComentarios);
        assertTrue(listaComentarios.size() > 0);
        assertEquals(1, listaComentarios.size());

    } // "FindByUsuario"

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        Integer busca = 1;
        ComentarioEntity comentarioEntity = getComentarioEntity();
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentarioEntity));

        // ACT - Ação, ou seja, testar o método.
        ComentarioDTO comentarioDTO = comentarioService.findById(busca);
        // ASSERT - Verificação do método.
        assertNotNull(comentarioDTO);
        assertNotNull(comentarioDTO.getIdComentario());
        assertEquals(1,comentarioDTO.getIdComentario());
    } // findByIdSucesso

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {

        // SETUP - Criar variáveis.
        Integer busca = 10;
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        // ACT - Ação, ou seja, testar o método.
        ComentarioDTO comentarioDTO = comentarioService.findById(busca);

        // ASSERT - Verificação do método.
        assertNull(comentarioDTO);

    } // findByIdErrado

    private static ComentarioDTO getComentarioDTO() {
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setComentario("eu também gosto de dragon ball!");
        comentarioDTO.setIdComentario(1);
        comentarioDTO.setIdUsuario(1);
        return comentarioDTO;
    }
    private static ComentarioEntity getComentarioEntity() {
        ComentarioEntity comentarioEntity = new ComentarioEntity();
        comentarioEntity.setComentario("eu também gosto de dragon ball!");
        comentarioEntity.setUsuario(getUsuarioEntity());
        comentarioEntity.setIdComentario(1);
        return comentarioEntity;
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

